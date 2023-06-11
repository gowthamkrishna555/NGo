package com.example.ngonew;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import android.widget.ListView;


public class VolunteerActivity extends AppCompatActivity {

    private ListView eventListView;
    private List<Event> eventList;
    private EventAdapter eventAdapter;
    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);

        eventListView = findViewById(R.id.eventListView);
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(this, eventList);
        eventListView.setAdapter(eventAdapter);

        // Retrieve event details from Firebase database
        databaseReference = FirebaseDatabase.getInstance().getReference("ngoDetails");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Event event = dataSnapshot.getValue(Event.class);
                eventList.add(event);
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle event details change if needed
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // Handle event removal if needed
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                // Handle event movement if needed
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(VolunteerActivity.this, "Failed to retrieve event details.", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle item click on the event list
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle joining as a member for the selected event
                Event selectedEvent = eventList.get(position);
                joinEvent(selectedEvent);
            }
        });
    }

    private void joinEvent(Event event) {
        // Add your logic here to join as a member for the event
        Toast.makeText(this, "Joined event on " + event.getEventDate(), Toast.LENGTH_SHORT).show();
    }
}
