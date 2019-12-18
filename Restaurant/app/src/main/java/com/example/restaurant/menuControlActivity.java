package com.example.restaurant;
import android.content.Context;
import android.content.Intent;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.Iterator;

public class menuControlActivity extends AppCompatActivity {
    class User {
        String form, name, price;

        User(String form, String name, String price) {
            this.form = form;
            this.name = name;
            this.price = price;
        }
    }

    String corner;
    String[] parent_path = corner.split("_");
    int data_count = 0;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_control);

        final ArrayList<User> mItem = new ArrayList<>();
        final ListAdapter adapter = new ListAdapter(this, mItem);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        Button addImage_button = findViewById(R.id.addImage_button);
        addImage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(nextIntent);
                finish();
            }
        });

        ref.child(corner).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (data_count == 0) {
                    Log.d("TAG", "dataSnapshot");

                    for (DataSnapshot i : dataSnapshot.getChildren()) {
                        User userdata = new User(i.child("form").getValue().toString(),
                                i.child("name").getValue().toString(),
                                i.child("price").getValue().toString());
                        Log.d("TAG", "forLoop");
                        mItem.add(userdata);
                    }
                    adapter.notifyDataSetChanged();
                    data_count = data_count + 1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //TODO("not implemented");
            }
        });
    }

    private class ViewHolder {
        ImageView image;
        TextView text;
        Button button;
    }

    class ListAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<User> item;

        ListAdapter(Context mcontext, ArrayList<User> mItem) {
            context = mcontext;
            item = mItem;
        }

        @Override
        public int getCount() {
            return item.size();
        }

        @Override
        public Object getItem(int position) {
            return item.get(position);
        }

        @Override
        public long getItemId(int position) {
            return (long) position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            final ViewHolder holder;

            if (convertView == null) {
                view = LayoutInflater.from(context).inflate(R.layout.row2, null);
                holder = new ViewHolder();
                holder.image = view.findViewById(R.id.imageView);
                holder.text = view.findViewById(R.id.textView3);
                holder.button = view.findViewById(R.id.button);
                view.setTag(holder);
            } else {
                view = convertView;
                return view;
            }

            final User mItem = item.get(position);
            String test = mItem.name + "\n" + mItem.price + "원";
            holder.text.setText(test);

            final StorageReference storageRef = storage.getReference().child(parent_path[0]).child(mItem.name + "." + mItem.form);
            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener() {
                public final void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Glide.with(menuControlActivity.this).load(task.getResult()).into(holder.image);
                    }
                }
            });

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ref.child(corner).child(mItem.name).removeValue();
                            Toast.makeText(getApplicationContext(), "삭제", Toast.LENGTH_SHORT).show();
                            Intent nextIntent = new Intent(getApplicationContext(), menuControlActivity.class);
                            startActivity(nextIntent);
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            return view;
        }
    }
}