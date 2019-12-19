package com.student.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import org.w3c.dom.Text;

public class studentActivity extends AppCompatActivity {
    private static String select_corner = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_page);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final Button btn_balance = (Button)findViewById(R.id.btn_balance);
        btn_balance.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent nextIntent = new Intent(studentActivity.this,chargeActivity.class);
                startActivity(nextIntent);
            }
        });

        btn_balance.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_balance.setBackgroundResource(R.drawable.chargebutton);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_balance.setBackgroundResource(R.drawable.chargebutton);
                }

                return false;
            }
        });

        final Button btn_A = (Button)findViewById(R.id.btn_A);
        btn_A.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                select_corner = "A_menu";
                Intent nextIntent = new Intent(studentActivity.this, cornerActivity.class);
                nextIntent.putExtra("select", select_corner);
                startActivity(nextIntent);
            }
        });

        btn_A.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_A.setBackgroundResource(R.drawable.aaa);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_A.setBackgroundResource(R.drawable.a);
                }

                return false;
            }
        });

        final Button btn_B = (Button)findViewById(R.id.btn_B);
        btn_B.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                select_corner = "B_menu";
                Intent nextIntent = new Intent(studentActivity.this, cornerActivity.class);
                nextIntent.putExtra("select", select_corner);
                startActivity(nextIntent);
            }
        });

        btn_B.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_B.setBackgroundResource(R.drawable.bbb);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_B.setBackgroundResource(R.drawable.bb);
                }

                return false;
            }
        });

        final Button btn_C = (Button)findViewById(R.id.btn_C);
        btn_C.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                select_corner = "C_menu";
                Intent nextIntent = new Intent(studentActivity.this, cornerActivity.class);
                nextIntent.putExtra("select", select_corner);
                startActivity(nextIntent);
            }
        });

        btn_C.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_C.setBackgroundResource(R.drawable.ccc);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_C.setBackgroundResource(R.drawable.cc);
                }

                return false;
            }
        });

        final Button btn_D = (Button)findViewById(R.id.btn_D);
        btn_D.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                select_corner = "D_menu";
                Intent nextIntent = new Intent(studentActivity.this, cornerActivity.class);
                nextIntent.putExtra("select", select_corner);
                startActivity(nextIntent);
            }
        });

        btn_D.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn_D.setBackgroundResource(R.drawable.ddd);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btn_D.setBackgroundResource(R.drawable.dd);
                }

                return false;
            }
        });


        Button btn_order = (Button)findViewById(R.id.btn_order);
        btn_order.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent nextIntent = new Intent(studentActivity.this, order_checkActivity.class);
                startActivity(nextIntent);
            }
        });

        //database

        DatabaseReference charge = database.getReference("student").child(student.INSTANCE.getID()).child("money");
        DatabaseReference order = database.getReference("student").child(student.INSTANCE.getID()).child("order");
        charge.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue();
                TextView var = findViewById(R.id.textView3);
                var.setText((CharSequence)(value.toString() + " 원"));
                student st = student.INSTANCE;
                String money = value.toString();
                Toast.makeText(studentActivity.this, money, Toast.LENGTH_SHORT);
                student var2 = st;
                int var3 = Integer.parseInt(money);
                var2.setMoney(var3);
                student.INSTANCE.setMoney(Integer.parseInt(String.valueOf(value)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        order.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object order_state = dataSnapshot.getValue();
                student.INSTANCE.setOrder(String.valueOf(order_state));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static final String getSelect_corner() {
        return select_corner;
    }

    public static final void setSelect_corner(String var0) { select_corner = var0; }
}


