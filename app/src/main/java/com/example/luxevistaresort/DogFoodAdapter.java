package com.example.luxevistaresort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class DogFoodAdapter extends RecyclerView.Adapter<DogFoodAdapter.DogFoodViewHolder> {

    private List<DogFood> dogFoodList;
    private Context context;

    public DogFoodAdapter(List<DogFood> dogFoodList, Context context) {
        this.dogFoodList = dogFoodList;
        this.context = context;
    }

    @NonNull
    @Override
    public DogFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item, parent, false);
        return new DogFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogFoodViewHolder holder, int position) {
        DogFood dogFood = dogFoodList.get(position);

        holder.itemName.setText(dogFood.getName());
        holder.itemDescription.setText(dogFood.getDescription());
        holder.itemPrice.setText("Rs. " + dogFood.getPrice());
        Glide.with(context).load(dogFood.getImageUrl()).into(holder.itemImage);

        CartItem cartItem = new CartItem(
                dogFood.getName(),
                dogFood.getImageUrl(),
                dogFood.getPrice(),
                1 // Default quantity
        );

        holder.itemQuantity.setText(String.valueOf(cartItem.getQuantity()));

        holder.addToCartButton.setOnClickListener(v -> {
            CartManager.getInstance().addItem(cartItem);
        });

        holder.decreaseQuantityButton.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                holder.itemQuantity.setText(String.valueOf(cartItem.getQuantity()));
                holder.itemPrice.setText("Rs. " + cartItem.getTotalPrice());
            }
        });

        holder.increaseQuantityButton.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            holder.itemQuantity.setText(String.valueOf(cartItem.getQuantity()));
            holder.itemPrice.setText("Rs. " + cartItem.getTotalPrice());
        });
    }

    @Override
    public int getItemCount() {
        return dogFoodList.size();
    }

    public static class DogFoodViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemDescription, itemPrice, itemQuantity;
        ImageView itemImage;
        Button addToCartButton;
        ImageButton decreaseQuantityButton, increaseQuantityButton;

        public DogFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.itemName);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemImage = itemView.findViewById(R.id.itemImage);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            decreaseQuantityButton = itemView.findViewById(R.id.decreaseQuantityButton);
            increaseQuantityButton = itemView.findViewById(R.id.increaseQuantityButton);
        }
    }
}
