package com.example.luxevistaresort;

public class CartItem {
    private String name;
    private String imageUrl;
    private String price;
    private int quantity;

    public CartItem() {
        // Default constructor required for Firebase
    }

    public CartItem(String name, String imageUrl, String price, int quantity) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity){
        this.quantity = quantity;
    }

    public double getFormattedPrice(){
        try {
            double parsedPrice = Double.parseDouble(price); // Convert price from String to double
            return parsedPrice;
        } catch (NumberFormatException e) {
            System.err.println("Invalid price format: " + price);
            return 0.0; // Handle the case where price is not a valid number
        }
    }
    public double getTotalPrice() {
        try {
            double parsedPrice = Double.parseDouble(price); // Convert price from String to double
            return parsedPrice * quantity;
        } catch (NumberFormatException e) {
            System.err.println("Invalid price format: " + price);
            return 0.0; // Handle the case where price is not a valid number
        }
    }
}
