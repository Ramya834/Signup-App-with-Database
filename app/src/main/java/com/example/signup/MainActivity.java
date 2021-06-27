package com.example.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    EditText email;
    EditText phoneNo;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser()  != null){
            Intent i = new Intent(MainActivity.this,DashboardActivity.class);
            startActivity(i);
        }

        Button button = findViewById(R.id.button);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        TextView textView = findViewById(R.id.signinhere);
        phoneNo = findViewById(R.id.phoneNo);

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("please wait");
        dialog.setTitle("Alert");
        dialog.setCancelable(false);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memail =email.getText().toString().trim();
                String mpassword = password.getText().toString().trim();
                String mphoneNo = phoneNo.getText().toString().trim();

                if (memail.isEmpty() || mpassword.isEmpty() || mphoneNo.isEmpty()){
                    Toast.makeText(MainActivity.this,"please provide data ",Toast.LENGTH_SHORT).show();

                }else if (mpassword.length()<6)
                    Toast.makeText(MainActivity.this, "password must contain 6 characters", Toast.LENGTH_SHORT).show();
                else {
                    auth.createUserWithEmailAndPassword(memail, mpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                                startActivity(i);

                            }else {
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });


    }
}