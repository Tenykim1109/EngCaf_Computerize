package com.student.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class order_checkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase var10000 = FirebaseDatabase.getInstance();
        final FirebaseDatabase database = var10000;
        setContentView(R.layout.order_check);
        //this.setContentView(-1300023);
        final Button button1  = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView tv = (TextView)findViewById(R.id.order_state_text);
                String state = tv.getText().toString();

                if(state.equals("")^true && state.equals("조리중")^ true && state.equals("주문수락대기중") ^true) {
                    database.getReference().child("student").child(student.INSTANCE.getID()).child("order").setValue("");
                    Toast.makeText((Context)order_checkActivity.this, (CharSequence)"수령되었습니다.",Toast.LENGTH_SHORT).show();
                    order_checkActivity.this.finish();
                }
                else{
                    Toast.makeText((Context)order_checkActivity.this,(CharSequence)"준비중입니다",Toast.LENGTH_SHORT).show();
                }

            }
        });
        button1.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button1.setBackgroundResource(R.drawable.getorder2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button1.setBackgroundResource(R.drawable.getorder);
                }

                return false;
            }
        });
        final Button button2  = (Button)findViewById(R.id.button2);

        button2.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button2.setBackgroundResource(R.drawable.confirm2);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button2.setBackgroundResource(R.drawable.confirm);
                }

                return false;
            }
        });
        DatabaseReference var4 = database.getReference("student").child(student.INSTANCE.getID()).child("order");
        DatabaseReference orderdata = var4;
        orderdata.addValueEventListener((ValueEventListener)new ValueEventListener(){
            public void onDataChange(@NotNull DataSnapshot dataSnapshot){
                Object value = dataSnapshot.getValue();
                TextView var10000 = (TextView)findViewById(R.id.order_state_text);
                var10000.setText((CharSequence)String.valueOf(value));
            }
            public void onCancelled(@NotNull DatabaseError p0) {

                String var2 = "not implemented";
                boolean var3 = false;
                //throw (Throwable)(new NotImplementedError("An operation is not implemented: " + var2));
            }
        });

    }
}