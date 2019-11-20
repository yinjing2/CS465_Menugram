package com.example.cs465_menugram;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties

public class Restaurant {
    private String RestaurantId;
    private String RestaurantName;
    private String RestaurantType;
    private List Tags;

    public Restaurant() {
        //this constructor is required
    }

    public Restaurant(String id, String name, String type) {
        this.RestaurantId = id;
        this.RestaurantName = name;
        this.RestaurantType = type;
    }

    public String getRestaurantId() {
        return RestaurantId;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public String getArtistType() {
        return RestaurantType;
    }
}