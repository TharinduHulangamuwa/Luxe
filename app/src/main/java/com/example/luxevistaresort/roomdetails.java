package com.example.luxevistaresort;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RoomDetailsActivity extends AppCompatActivity {

    private TextView roomDetailsTextView;
    private CalendarView calendarView;
    private Button bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        roomDetailsTextView = findViewById(R.id.roomDetailsTextView);
        calendarView = findViewById(R.id.calendarView);
        bookButton = findViewById(R.id.bookButton);

        String roomId = getIntent().getStringExtra("roomId");
        // Fetch room details using roomId and display them
        // For example:
        roomDetailsTextView.setText("Details for Room ID: " + roomId);

        bookButton.setOnClickListener(v -> {
            // Handle booking logic here
            Toast.makeText(RoomDetailsActivity.this, "Room booked successfully!", Toast.LENGTH_SHORT).show();
        });
    }
}