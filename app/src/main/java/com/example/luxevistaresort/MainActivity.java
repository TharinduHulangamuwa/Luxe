package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser ;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, nameEditText;
    private Button registerButton, loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);

        registerButton.setOnClickListener(v -> registerUser ());
        loginButton.setOnClickListener(v -> loginUser ());
    }

    private void registerUser () {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
            Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Correct method call
        // Correct method call without space
        mAuth.createUser WithEmailAndPassword(email, password)
            .addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                // Sign up success
                FirebaseUser  user = mAuth.getCurrentUser ();
                writeUser (user.getUid(), name, email);
                Toast.makeText(MainActivity.this, "Registration Successful.", Toast.LENGTH_SHORT).show();
            } else {
                // If sign up fails, display a message to the user.
                Toast.makeText(MainActivity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void writeUser (String userId, String name, String email) {
        User user = new User(name, email);
        database.child("users").child(userId).setValue(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Data written successfully
                    } else {
                        // Write failed
                        Toast.makeText(MainActivity.this, "Failed to save user data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loginUser () {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}