package com.example.restaurant;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.management_page);

        Button A_Button = findViewById(R.id.A_Button);
        A_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), select_cornerActivity.class);
                nextIntent.putExtra("menu", "A_menu");
                startActivity(nextIntent);
            }
        });

        Button B_Button = findViewById(R.id.B_Button);
        B_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), select_cornerActivity.class);
                nextIntent.putExtra("menu", "B_menu");
                startActivity(nextIntent);
            }
        });

        Button C_Button = findViewById(R.id.C_Button);
        C_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), select_cornerActivity.class);
                nextIntent.putExtra("menu", "C_menu");
                startActivity(nextIntent);
            }
        });

        Button D_Button = findViewById(R.id.D_Button);
        D_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), select_cornerActivity.class);
                nextIntent.putExtra("menu", "D_menu");
                startActivity(nextIntent);
            }
        });
    }
}
