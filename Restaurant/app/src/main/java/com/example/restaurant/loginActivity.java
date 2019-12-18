package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    private String id = "admin";
    private String password = "admin";
    Button login_button;
    EditText idText, pwdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        login_button = (Button)findViewById(R.id.login_button);
        idText = (EditText)findViewById(R.id.idText);
        pwdText = (EditText)findViewById(R.id.passwordText);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputId = idText.getText().toString();
                String inputPwd = pwdText.getText().toString();

                if(inputId.equals(id) && inputPwd.equals(password)) {
                    Intent nextIntent = new Intent(loginActivity.this, ManagementActivity.class);
                    startActivity(nextIntent);
                    Toast.makeText(loginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                }
                else if (inputId.isEmpty() && inputPwd.isEmpty()) {
                    Toast.makeText(loginActivity.this, "ID와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                else if(!inputId.equals(id)) {
                    Toast.makeText(loginActivity.this, "존재하지 않는 ID입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(loginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
