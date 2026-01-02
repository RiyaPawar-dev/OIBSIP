package com.example.to_dolist;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {

    private EditText etUser, etEmail, etPass;
    private MaterialButton btnSignup;
    private TextView tvLogin;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        etUser = findViewById(R.id.etUser);
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        btnSignup = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLogin);

        // Initialize DB
        db = new DBHelper(this);

        // Sign Up button click
        btnSignup.setOnClickListener(v -> {
            String username = etUser.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPass.getText().toString().trim();

            // Basic validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if username already exists
            if (db.checkUsername(username)) {
                Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert new user
            db.insertUser(username, password); // make sure your DBHelper has this method
            Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();

            finish(); // go back to LoginActivity
        });

        // Login link click
        tvLogin.setOnClickListener(v -> finish()); // just close activity to go back to login
    }
}
