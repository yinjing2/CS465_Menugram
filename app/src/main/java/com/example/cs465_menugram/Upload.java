package com.example.cs465_menugram;

public class Upload {
    private String name;
    private float rating;
    public String rn;
    private String url;
    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Upload() {
    }

    public Upload(String name, float rating, String restaurant_name, String url) {
        this.name = name;
        this.url= url;
        this.rating = rating;
        this.rn = restaurant_name;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getRestaurantName() { return rn;}

    public float getRating() {return rating; }
}
