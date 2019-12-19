package com.student.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import javax.annotation.Nonnull;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        final Button signup_button = (Button)findViewById(R.id.signup_button);
        signup_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent nextIntent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(nextIntent);
            }
        });
        final Button login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginEmail();
            }
        });


    }
    private final void loginEmail(){
        EditText edit_email = (EditText)findViewById(R.id.emailText);
        EditText edit_password =(EditText)findViewById(R.id.passwordText);
        final String email = edit_email.getText().toString();
        String password = edit_password.getText().toString();
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "email password를 입력하시오", Toast.LENGTH_SHORT).show();
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(
                    //email = edit_email.getText().toString(),
                    //password = edit_password.getText().toString()
                    email,password
            ).addOnCompleteListener(new OnCompleteListener() {
                public void onComplete(@Nonnull Task task) {
                    if (task.isSuccessful()) {
                        String user_temp[] = email.split("@");
                        student student = com.student.app.student.INSTANCE;
                        student.setID(user_temp[0]);
                        Intent nextIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(nextIntent);
                        Toast.makeText((Context) MainActivity.this, (CharSequence) "로그인 성공", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText((Context) MainActivity.this, (CharSequence) "로그인 실패 다시 시도하시오.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
