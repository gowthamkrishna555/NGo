package com.example.ngonew;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private EditText ngoNameEditText;
    private EditText descriptionEditText;
    private EditText eventsDescriptionEditText;
    private Button saveButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ngoNameEditText = findViewById(R.id.ngoName);
        descriptionEditText = findViewById(R.id.description);
        eventsDescriptionEditText = findViewById(R.id.eventsDescription);
        saveButton = findViewById(R.id.submitButton);

        // Initialize Firebase components
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ngoDetails");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNGODetails();
            }
        });

        // Fetch and display NGO details for the logged-in user
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = sanitizeEmailForFirebase(currentUser.getEmail());
            if (!TextUtils.isEmpty(userEmail)) {
                databaseReference.child(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            NGO ngo = dataSnapshot.getValue(NGO.class);
                            if (ngo != null) {
                                ngoNameEditText.setText(ngo.getNgoName());
                                descriptionEditText.setText(ngo.getDescription());
                                eventsDescriptionEditText.setText(ngo.getEventsDescription());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(MainActivity.this, "Failed to retrieve NGO details.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    private void saveNGODetails() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userEmail = sanitizeEmailForFirebase(currentUser.getEmail());
            if (!TextUtils.isEmpty(userEmail)) {
                String ngoName = ngoNameEditText.getText().toString().trim();
                String description = descriptionEditText.getText().toString().trim();
                String eventsDescription = eventsDescriptionEditText.getText().toString().trim();

                NGO ngo = new NGO(ngoName, description, eventsDescription);

                databaseReference.child(userEmail).setValue(ngo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "NGO details saved successfully.", Toast.LENGTH_SHORT).show();
                        } else {
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(MainActivity.this, "Failed to save NGO details: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    private String sanitizeEmailForFirebase(String email) {
        // Replace invalid characters in the email address
        return email.replace(".", "_dot_")
                .replace("#", "_hash_")
                .replace("$", "_dollar_")
                .replace("[", "_leftBracket_")
                .replace("]", "_rightBracket_");
    }
}
