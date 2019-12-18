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

        Button A_corner = findViewById(R.id.A_corner);
        A_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), corner_activity.class);
                nextIntent.putExtra("corner", "A_corner");
                startActivity(nextIntent);
            }
        });

        Button B_corner = findViewById(R.id.B_corner);
        B_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), corner_activity.class);
                nextIntent.putExtra("corner", "B_corner");
                startActivity(nextIntent);
            }
        });

        Button C_corner = findViewById(R.id.C_corner);
        C_corner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), corner_activity.class);
                nextIntent.putExtra("corner", "C_corner");
                startActivity(nextIntent);
            }
        });

        Button D_corner = findViewById(R.id.D_corner);
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
