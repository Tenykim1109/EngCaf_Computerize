package com.example.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private int PICK_IMAGE_REQUEST = 1234;
    private Uri filepath;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageButton btnChoose;
    Button btnUpload;
    EditText nameText, priceText;
    String select_corner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);
        btnChoose = (ImageButton)findViewById(R.id.btnChoose);
        btnUpload = (Button)findViewById(R.id.btnUpload);
        nameText = (EditText) findViewById(R.id.nameText);
        priceText = (EditText) findViewById(R.id.priceText);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        select_corner = getIntent().getStringExtra("select_corner");
        btnChoose.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnChoose:
                showFileChooser();
                break;
            case R.id.btnUpload:
                if(!nameText.getText().toString().isEmpty()&&!priceText.getText().toString().isEmpty())
                    uploadFile();
                else
                    Toast.makeText(this,"이름과 가격을 입력하시오",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode== Activity.RESULT_OK&&data!=null&&data.getData()!=null) {
            filepath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                Drawable drawable = new BitmapDrawable(bitmap);
                btnChoose.setBackground(drawable);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadFile() {
        if(filepath!=null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            final String name = nameText.getText().toString();
            final int price = Integer.parseInt(priceText.getText().toString());
            StorageReference imageRef = storageReference.child("A/" + name + ".jpg");
            imageRef.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference(select_corner);
                    myRef.child(name).child("form").setValue("jpg");
                    myRef.child(name).child("name").setValue(name);
                    myRef.child(name).child("price").setValue(price);
                    Toast.makeText(getApplicationContext(), "메뉴추가완료", Toast.LENGTH_SHORT).show();
                    Intent nextIntent = new Intent(getApplicationContext(), menuControlActivity.class);
                    nextIntent.putExtra("select_corner", select_corner);
                    startActivity(nextIntent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "실패 다시 시도하시오", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this,"이미지를 선택하세요.",Toast.LENGTH_SHORT).show();
        }
    }

    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "SELECT PICTURE"), PICK_IMAGE_REQUEST);
    }
}
