package com.example.cs465_menugram;

public class Upload {
    private String name;
    private String url;
    private float rating;
    private String restaurant_name;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, float rating, String restaurant_name, String url) {
        this.name = name;
        this.url= url;
        this.rating = rating;
        this.restaurant_name = restaurant_name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getRestaurantName() { return restaurant_name;}

    public float getRating() {return rating; }
}
