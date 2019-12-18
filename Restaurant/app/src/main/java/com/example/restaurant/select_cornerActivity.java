package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class select_cornerActivity extends AppCompatActivity{
    String select_corner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_corner_page);

        Button A_corner = (Button)findViewById(R.id.A_corner);
        Button B_corner = (Button)findViewById(R.id.B_corner);
        Button C_corner = (Button)findViewById(R.id.C_corner);
        Button D_corner = (Button)findViewById(R.id.D_corner);


        final Intent nextIntent = new Intent(this, cornerActivity.class);
        A_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_corner = "A_order";
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
            }
        });

        B_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_corner = "B_order";
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
            }
        });

        C_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_corner = "C_order";
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
            }
        });

        D_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_corner = "D_order";
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
            }
        });
    }
}
