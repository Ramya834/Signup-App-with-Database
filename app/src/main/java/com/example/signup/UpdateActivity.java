package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateActivity extends AppCompatActivity {
     FirebaseDatabase database;
     Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        database = FirebaseDatabase.getInstance();
         String uid = getIntent().getStringExtra("uid");
        EditText username = findViewById(R.id.uName);
        EditText phoneNo = findViewById(R.id.uPhoneNo);
        EditText city = findViewById(R.id.uCity);
        EditText Email = findViewById(R.id.uEmail);
        CircleImageView profile = findViewById(R.id.uProfile);
        Button button = findViewById(R.id.uBtn);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               database.getReference().child("users").child(uid).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       String mName = username.getText().toString();
                       String mPhoneNo = phoneNo.getText().toString();
                       String mCity = city.getText().toString();
                       String mEmail = Email.getText().toString();
                       String imageUri = uri.toString();


                       User user = new User(mEmail,mName,mPhoneNo,mCity,imageUri,uid);

                       database.getReference().child("users").child(uid).setValue(user);


                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
           }
       });

        database.getReference().child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child("username").getValue(String.class));
                Email.setText(snapshot.child("Email").getValue(String.class));
                city.setText(snapshot.child("city").getValue(String.class));
                phoneNo.setText(snapshot.child("phoneNo").getValue(String.class));
                uri = Uri.parse(snapshot.child("imageUri").getValue(String.class));

                Glide.with(UpdateActivity.this).load(uri).into(profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}