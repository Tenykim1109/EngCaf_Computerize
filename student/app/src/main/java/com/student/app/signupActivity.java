package com.student.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.view.View;


public class signupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        firebaseAuth = FirebaseAuth.getInstance();
        Button signUp_button2 = (Button)findViewById(R.id.signUp_button2);
        signUp_button2.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                signupActivity.this.createEmail();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent nextIntent = new Intent((Context)this, MainActivity.class);
        this.startActivity(nextIntent);
    }

    private final void createEmail() {
        EditText edit_email = (EditText)findViewById(R.id.signUp_emailText);
        EditText edit_password =(EditText)findViewById(R.id.signUp_passwordText);
        final String email = edit_email.getText().toString();
        String password =  edit_password.getText().toString();
        if(email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "email password를 입력하시오", Toast.LENGTH_SHORT).show();
        }

        else
        {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if (task.isSuccessful()) {
                        String[] id = email.split("@");
                        writeStudent(id[0]);

                        Toast.makeText(signupActivity.this, "회원가입 성공.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(signupActivity.this, "회원가입 실패 다시 시도하시오.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private final void writeStudent(String ID) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("student");
        student student = com.student.app.student.INSTANCE;
        student.setID(ID);
        student.setMoney(0);
        student.setOrder("");
        myRef.child(ID).setValue(student);
    }

}


