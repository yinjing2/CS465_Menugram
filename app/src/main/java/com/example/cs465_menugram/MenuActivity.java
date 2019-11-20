package com.example.cs465_menugram;

import android.app.AppComponentFactory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private String restaurantName;
    private TextView dishName1;
    private TextView dishName2;
    private TextView dishName3;
    private TextView dishName4;
    private TextView dishDescription1;
    private TextView dishDescription2;
    private TextView dishDescription3;
    private TextView dishDescription4;
    private TextView dishPrice1;
    private TextView dishPrice2;
    private TextView dishPrice3;
    private TextView dishPrice4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            restaurantName = extras.getString("rest_name");
            Log.d("MENU_ACTIVITY", "RestaurantName is: " + restaurantName);
        }

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Restaurants/"+restaurantName+"/Menu/");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>)dataSnapshot.getValue();
                Log.d("MENU_ACTIVITY", "Map is: " + map.toString());
                Log.d("MENU_ACTIVITY", "Map keys are: " + map.keySet().toString());

                Set<String> restaurant_name_set = map.keySet();


                Log.d("MENU_ACTIVITY", (String)map.get((String)restaurant_name_set.toArray()[0]));


                //Set names of textViews
                dishName1 = (TextView)findViewById(R.id.dishOne);
                dishName1.setText((String)restaurant_name_set.toArray()[0]);

                dishName2 = (TextView)findViewById(R.id.dishOne);
                dishName2.setText((String)restaurant_name_set.toArray()[0]);

                dishName3 = (TextView)findViewById(R.id.dishThree);
                dishName3.setText((String)restaurant_name_set.toArray()[2]);

                dishName4 = (TextView)findViewById(R.id.dishFour);
                dishName4.setText((String)restaurant_name_set.toArray()[3]);


//                (TextView)findViewById(R.id.priceOne);
//                (TextView)findViewById(R.id.priceTwo);
//                (TextView)findViewById(R.id.priceThree);
//                (TextView)findViewById(R.id.priceFour);
//                (TextView)findViewById(R.id.descriptionOne);
//                (TextView)findViewById(R.id.descriptionTwo);
//                (TextView)findViewById(R.id.descriptionThree);
//                (TextView)findViewById(R.id.descriptionFour);
//
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MENU_ACTIVITY", "Failed to read value.", error.toException());
            }
        });

    }


}
