package com.example.cs465_menugram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RestaurantActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton threeDots;
    private ImageButton camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        threeDots = (ImageButton)findViewById(R.id.threeDotImage);
        camera = (ImageButton)findViewById(R.id.camera);

        threeDots.setOnClickListener(this);
        camera.setOnClickListener(this);


        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();

        StorageReference islandRef = mStorageRef.child("foodImages/m1.jpg");

        File localFile = null;
        try {
            localFile = File.createTempFile("newM1", "jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

        islandRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();


        myRef.child("restaurantAddresses").addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d("SEARCH_ACTIVITY", "Value is: " + map);

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w("SEARCH_ACTIVITY", "searching database FAILED");
            }

        });
    }

    public void onClick(View v){
        if(v==threeDots){
            Intent intent = new Intent(this, ShowImagesActivity.class);
            startActivity(intent);
        } else if (v == camera) {
            Intent intent = new Intent(this, ReviewActivity.class);
            startActivity(intent);
        }
    }
}
