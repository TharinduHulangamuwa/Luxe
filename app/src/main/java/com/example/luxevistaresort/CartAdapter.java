package com.example.luxevistaresort;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private Context context;
    private OnItemRemoveListener onItemRemoveListener;

    // Interface for handling item removal
    public interface OnItemRemoveListener {
        void onItemRemoved(int position);
    }

    public CartAdapter(List<CartItem> cartItems, Context context, OnItemRemoveListener listener) {
        this.cartItems = cartItems;
        this.context = context;
        this.onItemRemoveListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.itemName.setText(cartItem.getName());
        holder.itemPrice.setText("Rs. " + cartItem.getTotalPrice());
        holder.itemQuantity.setText("Quantity: " + cartItem.getQuantity());
        Glide.with(context).load(cartItem.getImageUrl()).into(holder.itemImage);

        holder.removeButton.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            if (onItemRemoveListener != null) {
                onItemRemoveListener.onItemRemoved(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, itemQuantity;
        ImageView itemImage;
        Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.cartItemName);
            itemPrice = itemView.findViewById(R.id.cartItemPrice);
            itemQuantity = itemView.findViewById(R.id.cartItemQuantity);
            itemImage = itemView.findViewById(R.id.cartItemImage);
            removeButton = itemView.findViewById(R.id.removeFromCartButton);
        }
    }
}
