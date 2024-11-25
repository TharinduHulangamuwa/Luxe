package com.example.luxevistaresort;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";

    private RecyclerView foodItemsRecyclerView;
    private DogFoodAdapter dogFoodAdapter;
    private List<DogFood> dogFoodList;
    private TextView usernameText;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameText = findViewById(R.id.usernameText);

        // Retrieve username from SharedPreferences if not passed via Intent
        String username = getIntent().getStringExtra("username");

        currentUser = (User) getIntent().getSerializableExtra("user");

        if (username == null) {
            username = getSharedPreferences("MyPupCare", MODE_PRIVATE).getString("username", "Guest");
        }

        if (username != null) {
            usernameText.setText(username);
        }

        // Initialize the RecyclerView
        foodItemsRecyclerView = findViewById(R.id.foodItemsRecyclerView);
        foodItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dogFoodList = new ArrayList<>();
        dogFoodAdapter = new DogFoodAdapter(dogFoodList, this);
        foodItemsRecyclerView.setAdapter(dogFoodAdapter);


        // Fetch data from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("dogFood")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            DogFood dogFood = document.toObject(DogFood.class);
                            dogFoodList.add(dogFood);

                        }
                        dogFoodAdapter.notifyDataSetChanged();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                });

        //Go To Cart Cart Activity
        ImageView cartIcon = findViewById(R.id.cartIcon);
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        //Go To article section
        ImageView bookIcon = findViewById(R.id.bookIcon);
        bookIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ArticleActivity.class);
                startActivity(intent);
            }
        });

        ImageView profileIcon = findViewById(R.id.profileIcon);
        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfilePopup();
            }
        });
    }

    private void showProfilePopup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_profile);
        dialog.setCancelable(true);

        // Set user details to the TextViews
        TextView usernameTextView = dialog.findViewById(R.id.usernameTextView);
        TextView emailTextView = dialog.findViewById(R.id.emailTextView);
        TextView addressTextView = dialog.findViewById(R.id.addressTextView);


        if (currentUser != null) {
            usernameTextView.setText(currentUser.getUsername());
            emailTextView.setText(currentUser.getEmail());

            String address = currentUser.getAddress();
            if (address == null || address.isEmpty()) {
                addressTextView.setText("Address is not provided");
            } else {
                addressTextView.setText(address);
            }
        }

        Button closeButton = dialog.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}