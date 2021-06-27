package com.example.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
      FirebaseAuth auth;
      FirebaseDatabase database;
      FirebaseStorage storage;
      CircleImageView circleImageView;
         Uri uri;
    private Object CircleImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        EditText username = findViewById(R.id.username);
        EditText phoneNo = findViewById(R.id.phoneNo);
        EditText city = findViewById(R.id.city);
        EditText email = findViewById(R.id.email);
        Button button = findViewById(R.id.nbutton);
        CircleImageView = findViewById(R.id.CircularimageView);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, 1);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mUsername = username.getText().toString().trim();
                String mCity = city.getText().toString().trim();
                String mPhoneNo = phoneNo.getText().toString().trim();

                StorageReference reference = storage.getReference().child("profile").child(auth.getCurrentUser().getUid());
                 reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                     @Override
                     public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageuri = uri.toString();
                                    if (mEmail.isEmpty() && mUsername.isEmpty() && mCity.isEmpty()) {
                                        Toast.makeText(HomeActivity.this, "provide valid data", Toast.LENGTH_SHORT).show();
                                    } else {
                                        User user = new User(mEmail, mUsername, mPhoneNo, mCity,imageuri,auth.getCurrentUser().getUid());
                                        database.getReference().child("users").child(auth.getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent i = new Intent(HomeActivity.this, DashboardActivity.class);
                                                startActivity(i);
                                                Toast.makeText(HomeActivity.this, "successful!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                }
                            });
                        }
                     }
                 });





            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK){
            if (data != null){
               uri = data.getData();
               circleImageView.setImageURI(uri);
            }
        }

    }
}


