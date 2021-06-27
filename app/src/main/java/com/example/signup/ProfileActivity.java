package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        database = FirebaseDatabase.getInstance();



        String uid = getIntent().getStringExtra("uid");
        TextView username = findViewById(R.id.pusername);
        TextView Email = findViewById(R.id.pEmail);
        TextView phoneNo = findViewById(R.id.pPhoneNo);
        TextView city  = findViewById(R.id.pcity);
        CircleImageView profile = findViewById(R.id.circleimageView);
        Button button = findViewById(R.id.pUpdateBtn);
        Button deleteUser = findViewById(R.id.deleteUser);

        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Alert!");
                builder.setMessage("do you want to delete?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      database.getReference().child("users").child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()){
                               Intent i = new Intent(ProfileActivity.this, DashboardActivity.class);
                               Toast.makeText(ProfileActivity.this,"User deleted",Toast.LENGTH_SHORT).show();
                           }else{
                               Toast.makeText(ProfileActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                           }
                          }
                      });
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProfileActivity.this,"Great choice!",Toast.LENGTH_SHORT).show();

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,UpdateActivity.class);
                i.putExtra("uid",uid);
                startActivity(i);
            }
        });

        database.getReference().child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username.setText(snapshot.child("username").getValue(String.class));
                Email.setText(snapshot.child("Email").getValue(String.class));
                city.setText(snapshot.child("city").getValue(String.class));
                phoneNo.setText(snapshot.child("phoneNo").getValue(String.class));

                Glide.with(ProfileActivity.this).load(Uri.parse(snapshot.child("imageUri").getValue(String.class))).into(profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}