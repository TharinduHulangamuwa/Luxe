package com.example.luxevistaresort;

import java.util.ArrayList;
import java.util.List;

public class CartManager {

    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addItem(CartItem newItem) {
        for (CartItem item : cartItems) {
            if (item.getName().equals(newItem.getName())) {
                // Update quantity and total price for existing item
                item.setQuantity(item.getQuantity() + newItem.getQuantity());
                return;
            }
        }
        // If not found, add new item to the list
        cartItems.add(newItem);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);
    }
}
