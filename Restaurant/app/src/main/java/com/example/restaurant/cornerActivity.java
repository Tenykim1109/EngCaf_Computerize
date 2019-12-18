package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

public class cornerActivity extends AppCompatActivity {

   private DatabaseReference ref;
   ListView listView;
   String select_corner;

    @IgnoreExtraProperties
    public class User {

        public String menuNum;
        public String studentid;
        public String menustate;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String menuNum, String studentid, String menustate) {
            this.menuNum = menuNum;
            this.studentid = studentid;
            this.menustate = menustate;
        }

    }

    String start = "1";
    String serve = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.corner_page);

        listView = (ListView)findViewById(R.id.listView);
        select_corner = getIntent().getStringExtra("select_corner");
        final ArrayList<User> mItem = new ArrayList<User>();
        final ListAdapter adapter = new ListAdapter(this, mItem);
        listView.setAdapter(adapter);
        ref = FirebaseDatabase.getInstance().getReference();

        ref.child(select_corner).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User userdata = new User(dataSnapshot.child("menu").getValue().toString(), dataSnapshot.child("studentID").getValue().toString(), dataSnapshot.child("state").getValue().toString());
                mItem.add(userdata);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public class ListAdapter extends BaseAdapter {
        private ArrayList<User> mItem;
        private int listCnt;
        private Context mContext;
        LayoutInflater inflater;

        public ListAdapter(Context context, ArrayList<User> list) {
            mContext = context;
            mItem = list;
            listCnt = mItem.size();
        }

        @Override
        public int getCount() {
            Log.i("TAG", "getCount");
            return listCnt;
        }

        @Override
        public Object getItem(int i) {
            return mItem.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            View view = convertView;
            if(convertView == null) {
                inflater = LayoutInflater.from(mContext);
                view = inflater.inflate(R.layout.row, container, false);
            }

            TextView text = (TextView)findViewById(R.id.textView3);
            final Button button1 = (Button)findViewById(R.id.button1);

            text.setText(mItem.get(position).menuNum);
            if(mItem.get(position).menustate.equals(start))
                button1.setText("승인");
            else
                button1.setText("제공");

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User clickbutton = mItem.get(position);
                    int check = 0;
                    if(check==0&&clickbutton.menustate.equals(start)) {
                        button1.setText("제공");
                        mItem.get(position).menustate="2";
                        ref.child(select_corner).child(clickbutton.studentid).child("state").setValue(2);
                        ref.child("student").child(clickbutton.studentid).child("order").setValue("조리중");
                        check++;
                    }
                    if(check==0&&clickbutton.menustate.equals(serve)) {
                        ref.child("student").child(clickbutton.studentid).child("order").setValue(clickbutton.menuNum + " 조리완료");
                        ref.child(select_corner).child(clickbutton.studentid).removeValue();
                        mItem.remove(clickbutton);
                    }
                    notifyDataSetChanged();
                }
            });
            return view;
        }

    }
}
