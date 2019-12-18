package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementActivity extends AppCompatActivity {
    Button A_button, B_button, C_button, D_button;
    String select_corner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_page);

        A_button = (Button)findViewById(R.id.A_Button);
        B_button = (Button)findViewById(R.id.B_Button);
        C_button = (Button)findViewById(R.id.C_Button);
        D_button = (Button)findViewById(R.id.D_Button);


        A_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_corner = "A_menu";
                Intent nextIntent = new Intent(ManagementActivity.this, menuControlActivity.class);
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
            }
        });

        B_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_corner = "B_menu";
                Intent nextIntent = new Intent(ManagementActivity.this, menuControlActivity.class);
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
            }
        });

        C_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_corner = "C_menu";
                Intent nextIntent = new Intent(ManagementActivity.this, menuControlActivity.class);
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
            }
        });

        D_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                select_corner = "D_menu";
                Intent nextIntent = new Intent(ManagementActivity.this, menuControlActivity.class);
                nextIntent.putExtra("select_corner", select_corner);
                startActivity(nextIntent);
            }
        });
    }
}
