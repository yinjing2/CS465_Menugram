package com.example.cs465_menugram;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public String Restaurant_Name = "Sakanaya";
    public static boolean true_clicked = false;
    public static boolean false_clicked = false;

    private Button reviewButton;
    private Button searchButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reviewButton = (Button) findViewById(R.id.button);
        searchButton = (Button) findViewById(R.id.search_page_button);


        reviewButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            true_clicked = true;
            Intent intent = new Intent(this, RestaurantActivity.class);
            startActivity(intent);
        } else if(v.getId() == R.id.search_page_button) {
            Log.d("MAIN_ACTIVITY","detected search_page_button id");
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            Log.d("MAIN_ACTIVITY", "Created intent for Search activity");
            startActivity(intent);
        }
    }
}
