package com.example.restaurant;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button start_button = findViewById(R.id.start_button);
       start_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent nextIntent = new Intent(getApplicationContext(), select_cornerActivity.class);
               startActivity(nextIntent);
           }
       });

        Button login_button = findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(nextIntent);
            }
        });
    }
}
