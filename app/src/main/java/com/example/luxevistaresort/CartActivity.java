package com.example.luxevistaresort;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemRemoveListener {

    private List<CartItem> cartItems;
    private CartAdapter cartAdapter;
    private TextView totalPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItems = CartManager.getInstance().getCartItems();
        totalPriceTextView = findViewById(R.id.totalPrice);
        cartAdapter = new CartAdapter(cartItems, this, this);
        cartRecyclerView.setAdapter(cartAdapter);

        updateTotalPrice(cartItems, totalPriceTextView);

        ImageView homeIcon = findViewById(R.id.homeIcon);
        homeIcon.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        ImageView bookIcon = findViewById(R.id.bookIcon);
        bookIcon.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, ArticleActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.checkoutButton).setOnClickListener(v -> {
            if (cartItems.isEmpty()) {
                // Show the "Please add items before checkout" message
                Toast.makeText(CartActivity.this, "Please add items before checkout", Toast.LENGTH_SHORT).show();
            } else {
                // Show the "Order Placed" message
                Toast.makeText(CartActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();

            }
                // Clear the cart
            CartManager.getInstance().clearCart();
            cartAdapter.notifyDataSetChanged();
            updateTotalPrice(cartItems, totalPriceTextView);
        });
    }

    @Override
    public void onItemRemoved(int position) {
        updateTotalPrice(cartItems, totalPriceTextView);
    }

    private void updateTotalPrice(List<CartItem> cartItems, TextView totalPriceTextView) {
        double totalPrice = calculateTotalPrice(cartItems);
        totalPriceTextView.setText(String.format("Total Price: Rs. %.2f", totalPrice));
    }

    private double calculateTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0;
        for (CartItem item : cartItems) {
            totalPrice += item.getFormattedPrice() * item.getQuantity();
        }
        return totalPrice;
    }
}
