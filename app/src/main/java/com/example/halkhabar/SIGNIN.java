package com.example.halkhabar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.halkhabar.databinding.ActivitySignUpBinding;
import com.example.halkhabar.databinding.ActivitySigninBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SIGNIN extends AppCompatActivity {
    private ActivitySigninBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.Signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(binding.email.getText().toString(), binding.password.getText().toString());
            }
        });

        binding.first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noaccount();
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            goToHomeActivity();
                        } else {
                            Toast.makeText(SIGNIN.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(SIGNIN.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(SIGNIN.this, Home.class);
        startActivity(intent);
        finish(); // Optional: Close the current activity to prevent going back to it by pressing the back button
    }

    private void noaccount() {
        Intent intent = new Intent(SIGNIN.this, SignUP.class);
        startActivity(intent);
        finish(); // To prevent going back with back button
    }
}
