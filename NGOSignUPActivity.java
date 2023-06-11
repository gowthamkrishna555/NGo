package com.example.ngonew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class NGOSignUPActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, signupConfirmPassword, signupUsername, signupPhone, signupGovernmentId;
    private Button signupButton;
    private TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngosign_upactivity);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPhone = findViewById(R.id.signup_phone);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirmPassword = findViewById(R.id.signup_confirm_passwords);
        signupGovernmentId = findViewById(R.id.signup_government_id);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signupEmail.getText().toString().trim();
                String username = signupUsername.getText().toString().trim();
                String phone = signupPhone.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String confirmPassword = signupConfirmPassword.getText().toString().trim();
                String governmentId = signupGovernmentId.getText().toString().trim();

                // Validate the input fields
                if (email.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                    return;
                }
                if (username.isEmpty()) {
                    signupUsername.setError("Username cannot be empty");
                    return;
                }
                if (phone.isEmpty()) {
                    signupPhone.setError("Phone number cannot be empty");
                    return;
                }
                if (phone.length() != 10) {
                    signupPhone.setError("Invalid phone number format");
                    return;
                }
                if (username.matches("\\d+")) {
                    signupUsername.setError("Username cannot be just numbers");
                    return;
                }
                if (password.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    signupConfirmPassword.setError("Confirm Password cannot be empty");
                    return;
                }
                if (governmentId.isEmpty()) {
                    signupGovernmentId.setError("Government ID cannot be empty");
                    return;
                }
                if (!isValidEmail(email)) {
                    signupEmail.setError("Invalid email format");
                    return;
                }
                if (!isPasswordValid(password)) {
                    signupPassword.setError("Invalid password format");
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    signupConfirmPassword.setError("Passwords do not match");
                    return;
                }

                // Perform the sign-up operation
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Store the user data in the database
                            // Add your implementation here
                            Toast.makeText(NGOSignUPActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(NGOSignUPActivity.this, NGOLoginActivity.class));
                        } else {
                            Toast.makeText(NGOSignUPActivity.this, "Sign Up Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NGOSignUPActivity.this, NGOLoginActivity.class));
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Validate email format using a regular expression
        String emailRegex = "^[A-Za-z0-9._%+-]+@gmail.com$";
        return email.matches(emailRegex);
    }

    private boolean isPasswordValid(String password) {
        // Validate password format
        // Password should have a minimum of 8 characters, at least one uppercase letter,
        // one lowercase letter, one digit, and one special character
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordRegex);
    }
}
