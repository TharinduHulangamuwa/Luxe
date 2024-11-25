package com.example.luxevistaresort;

public class DogFood {
    private String name;
    private String description;
    private String imageUrl;
    private String price; // Use double for the price

    public DogFood() {
        // Default constructor required for Firebase
    }

    public DogFood(String name, String description, String imageUrl, String price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPrice() {
        return price;
    }
}
