package com.example.cs465_menugram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton pic;
    private TextView title;
    private TextView restName;

    // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            // This method is called once with the initial value and again
//            // whenever data at this location is updated.
//            String value = dataSnapshot.getValue(String.class);
//            Log.d("MAIN_ACTIVITY", "Value is: " + value);
//        }
//
//        @Override
//        public void onCancelled(DatabaseError error) {
//            // Failed to read value
//            Log.w(TAG, "Failed to read value.", error.toException());
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pic = (ImageButton) findViewById(R.id.dishOne);
        title = (TextView) findViewById(R.id.name);
        restName = (TextView) findViewById(R.id.restName);


//        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d("MAIN_ACTIVITY", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("MAIN_ACTIVITY", "Failed to read value.", error.toException());
//            }
//        });


        pic.setOnClickListener(this);
        title.setOnClickListener(this);
        restName.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.name || v.getId() == R.id.restName || v.getId() == R.id.dishOne) {
            TextView tView = findViewById(R.id.restName);
            Intent intent = new Intent(this, MenuActivity.class);
            intent.putExtra("rest_name", tView.getText());
            startActivity(intent);
        }
    }
    // Read from the database

}
