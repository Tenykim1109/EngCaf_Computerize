package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Objects;

public class menuControlActivity extends AppCompatActivity {
    @IgnoreExtraProperties
    public class User {
        String form, name, price;

        User(String form, String name, String price) {
            this.form = form;
            this.name = name;
            this.price = price;
        }
    }

    String select_corner;
    int data_count = 0;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_control);

        listView = findViewById(R.id.listView);
        final ArrayList<User> mItem = new ArrayList<>();
        select_corner = getIntent().getStringExtra("select_corner");
        final ListAdapter adapter = new ListAdapter(this, mItem);
        listView.setAdapter(adapter);

        final Button addImage_button = findViewById(R.id.addImage_button);
        addImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(menuControlActivity.this, AddActivity.class);
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
                finish();
            }
        });

        ref.child(select_corner).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (data_count == 0) {
                    Log.d("TAG", "dataSnapshot");

                    for(DataSnapshot child : dataSnapshot.getChildren()) {
                        User userdata = new User(
                                Objects.requireNonNull(child.child("form").getValue()).toString(),
                                Objects.requireNonNull(child.child("name").getValue()).toString(),
                                Objects.requireNonNull(child.child("price").getValue()).toString()
                                );
                        Log.d("TAG", "forLoop");
                        mItem.add(userdata);
                    }
                    adapter.notifyDataSetChanged();
                    data_count += 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        });
    }

    private class ViewHolder{
        ImageView image;
        TextView Text;
        Button button;
    }

    public class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater = null;
        private ArrayList<User> item;
        private Context mContext;
        String[] parent_path = select_corner.split("_");

        ListAdapter(Context context, ArrayList<User> list) {
            this.mContext = context;
            this.item = list;
        }

        @Override
        public int getCount() {
            Log.i("TAG", "getCount");
            return item.size();
        }

        @Override
        public Object getItem(int position) {
            return item.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            final ViewHolder holder;
            if (view == null) {
                if (inflater == null) {
                    inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                }
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.row2, parent, false);
                holder.image = view.findViewById(R.id.imageView);
                holder.Text = view.findViewById(R.id.textView3);
                holder.button = view.findViewById(R.id.button);
                view.setTag(holder);
            }
            else {
                holder = (ViewHolder) convertView.getTag();
                return view;
            }

            final User mitem = item.get(position);
            String test = mitem.name + "\n" + mitem.price + "원";
            holder.Text.setText(test);

            final StorageReference storageRef = storage.getReference().child(parent_path[0]).child(mitem.name+"."+mitem.form);
            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful())
                        Glide.with(menuControlActivity.this).load(task.getResult()).into(holder.image);
                }
            });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ref.child(select_corner).child(mitem.name).removeValue();
                            Toast.makeText(menuControlActivity.this, "삭제", Toast.LENGTH_SHORT).show();
                            Intent nextIntent = new Intent(menuControlActivity.this, menuControlActivity.class);
                            startActivity(nextIntent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(menuControlActivity.this,"실패.",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            return view;
        }
    }
}
