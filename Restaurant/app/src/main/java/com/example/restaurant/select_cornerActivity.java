package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class select_cornerActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_corner_page);

        final String selected_corner = "";
        Button A_corner = (Button)findViewById(R.id.A_corner);
        Button B_corner = (Button)findViewById(R.id.B_corner);
        Button C_corner = (Button)findViewById(R.id.C_corner);
        Button D_corner = (Button)findViewById(R.id.D_corner);
        A_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), corner_activity.class);
                nextIntent.putExtra("corner", "A_corner");
                startActivity(nextIntent);
            }
        });

        B_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), corner_activity.class);
                nextIntent.putExtra("corner", "B_corner");
                startActivity(nextIntent);
            }
        });

        C_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), corner_activity.class);
                nextIntent.putExtra("corner", "C_corner");
                startActivity(nextIntent);
            }
        });

        D_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), corner_activity.class);
                nextIntent.putExtra("corner", "D_corner");
                startActivity(nextIntent);
            }
        });



    }
}
