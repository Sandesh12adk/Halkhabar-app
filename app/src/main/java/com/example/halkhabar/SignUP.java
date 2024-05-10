package com.example.halkhabar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.halkhabar.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

public class SignUP extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        // Initialize ProgressBar


        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        binding.first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haveaccount();
            }
        });
    }

    private void registerUser() {
        // Show ProgressBar before registration process
        binding.progressBar.setVisibility(View.VISIBLE);

        String username = binding.username.getText().toString().trim();
        String email = binding.email.getText().toString().trim();
        String password = binding.password.getText().toString().trim();

        if (!email.matches(emailPattern)) {
            binding.email.setError("Please Enter a valid email address");
            binding.progressBar.setVisibility(View.GONE); // Hide ProgressBar
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            binding.password.setError("Password must contain at least 6 characters");
           binding. progressBar.setVisibility(View.GONE); // Hide ProgressBar
            return;
        }

        // Proceed with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    // Hide ProgressBar when registration process finishes
                    binding.progressBar.setVisibility(View.GONE);

                    if (task.isSuccessful()) {
                        // Sign up success, update UI
                        Toast.makeText(SignUP.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        // Redirect to Home activity
                        Intent intent = new Intent(SignUP.this, Home.class);
                        startActivity(intent);
                        finish(); // Finish the current activity to prevent going back to it on back press
                    } else {
                        // If sign up fails, display a message to the user
                        Toast.makeText(SignUP.this, "Authentication failed: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void haveaccount() {
        Intent intent = new Intent(SignUP.this, SIGNIN.class);
        startActivity(intent);
        finish(); // Finish the current activity to prevent going back to it on back press
    }
}
