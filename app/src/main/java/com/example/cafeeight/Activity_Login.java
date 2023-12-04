package com.example.cafeeight;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Login extends AppCompatActivity {

    private EditText email, password;
    private Button btnlogin;
    private DatabaseHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.TextEmail);
        password = findViewById(R.id.TextPassword);
        btnlogin = findViewById(R.id.buttonLogin);
        DB = new DatabaseHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(Activity_Login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    boolean checkUserPass = DB.checkEmailPassword(user, pass);
                    if (checkUserPass) {
                        Toast.makeText(Activity_Login.this, "Sign In Successful", Toast.LENGTH_SHORT).show();

                        // Start the main activity after successful login
                        Intent intent = new Intent(Activity_Login.this, Activity_Main.class);
                        startActivity(intent);

                        // Finish the current login activity so that pressing the back button doesn't go back to it
                        finish();
                    } else {
                        Toast.makeText(Activity_Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        TextView textSignUp = findViewById(R.id.TextSignUp);

        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login.this, Activity_Signup.class);
                startActivity(intent);
            }
        });

    }
}