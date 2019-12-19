package com.student.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class chargeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.charge_page);

        Button charge_button = (Button)findViewById(R.id.charge_button);
        charge_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                EditText var10000 = (EditText)chargeActivity.this.findViewById(R.id.moneyText);
                String money = var10000.getText().toString();
                CharSequence var3 = (CharSequence)money;
                boolean var4 = false;
                if (var3.length() == 0) {
                    Toast.makeText((Context)chargeActivity.this, (CharSequence)"충전금액을 입력하시오", 0).show();
                } else {
                    student var5 = student.INSTANCE;
                    var4 = false;
                    int var6 = Integer.parseInt(money);
                    var5.setMoney(var6 + student.INSTANCE.getMoney());
                    FirebaseDatabase var9 = FirebaseDatabase.getInstance();
                    FirebaseDatabase database = var9;
                    DatabaseReference var10 = database.getReference("student");
                    DatabaseReference myRef = var10;
                    myRef.child(student.INSTANCE.getID()).child("money").setValue(student.INSTANCE.getMoney());
                    Toast.makeText((Context)chargeActivity.this, (CharSequence)"금액충전 성공.", Toast.LENGTH_SHORT).show();
                    chargeActivity.this.finish();
                }
            }
        });


    }
}