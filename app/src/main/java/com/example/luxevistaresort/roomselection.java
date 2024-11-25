package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RoomSelectionActivity extends AppCompatActivity {

    private RecyclerView roomRecyclerView;
    private RoomAdapter roomAdapter;
    private List<Room> roomList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_selection);

        roomRecyclerView = findViewById(R.id.roomRecyclerView);
        roomRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        roomList = new ArrayList<>();
        roomAdapter = new RoomAdapter(roomList, this);
        roomRecyclerView.setAdapter(roomAdapter);

        fetchRooms();
    }

    private void fetchRooms() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("rooms")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Room room = document.toObject(Room.class);
                            roomList.add(room);
                        }
                        roomAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(RoomSelectionActivity.this, "Failed to fetch rooms.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}