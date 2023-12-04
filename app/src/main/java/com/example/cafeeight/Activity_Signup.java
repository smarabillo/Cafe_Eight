package com.example.cafeeight;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Signup extends AppCompatActivity {

    private EditText email, password, repassword;
    private Button signup, signin;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        email = findViewById(R.id.TextEmail);
        password = findViewById(R.id.TextPassword);
        repassword = findViewById(R.id.TextRePassword);
        signup = findViewById(R.id.buttonSignUp);
        signin = findViewById(R.id.buttonLogin);
        DB = new DatabaseHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String repass = repassword.getText().toString().trim();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(repass)) {
                    Toast.makeText(Activity_Signup.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else if (!pass.equals(repass)) {
                    Toast.makeText(Activity_Signup.this, "Password not matching!", Toast.LENGTH_SHORT).show();
                } else {
                    if (DB.checkEmail(user)) {
                        Toast.makeText(Activity_Signup.this, "User Already Exist! Please sign in", Toast.LENGTH_SHORT).show();
                    } else {
                        // Corrected method call
                        DB.insertData(user, pass);

                        Toast.makeText(Activity_Signup.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                        // Finish the current signup activity so that pressing the back button doesn't go back to it
                        finish();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Signup.this, Activity_Login.class);
                startActivity(intent);
            }
        });
    }
}
