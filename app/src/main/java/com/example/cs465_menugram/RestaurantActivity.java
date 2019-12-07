package com.example.cs465_menugram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.Iterator;

public class RestaurantActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton threeDots;
    private ImageButton camera;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
//        setupBottomNavigationView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        Toast toast = Toast.makeText(getApplicationContext(),"Slide left for menu",Toast.LENGTH_SHORT);
        // toast.setMargin(50,50);
        toast.setGravity(Gravity.TOP, 0,700);
        toast.show();



        camera = (ImageButton)findViewById(R.id.camera);


        camera.setOnClickListener(this);

        LinearLayout layout = (LinearLayout)findViewById(R.id.main_layout);
        layout.setOnTouchListener(new OnSlideTouchListener() {
            @Override
            public boolean onSlideLeft() {
                Intent intent = new Intent(RestaurantActivity.this, MenuActivity.class);
//                Log.d("resName", intent.getStringExtra("resName"));
//                intent.putExtra("RestaurantName", intent.getStringExtra("resName"));
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });


        final ImageView[] img = new ImageView[16];
        final ImageView imageView1 = (ImageView) findViewById(R.id.img1);
        img[0]=imageView1;
        final ImageView imageView2 = (ImageView) findViewById(R.id.img2);
        img[1]=imageView2;
        final ImageView imageView3 = (ImageView) findViewById(R.id.img3);
        img[2]=imageView3;
        final ImageView imageView4 = (ImageView) findViewById(R.id.img4);
        img[3]=imageView4;
        final ImageView imageView5 = (ImageView) findViewById(R.id.img5);
        img[4]=imageView5;
        final ImageView imageView6 = (ImageView) findViewById(R.id.img6);
        img[5]=imageView6;
        final ImageView imageView7 = (ImageView) findViewById(R.id.img7);
        img[6]=imageView7;
        final ImageView imageView8 = (ImageView) findViewById(R.id.img8);
        img[7]=imageView8;
        final ImageView imageView9 = (ImageView) findViewById(R.id.img9);
        img[8]=imageView9;
        final ImageView imageView10 = (ImageView) findViewById(R.id.img10);
        img[9]=imageView10;
        final ImageView imageView11 = (ImageView) findViewById(R.id.img11);
        img[10]=imageView11;
        final ImageView imageView12 = (ImageView) findViewById(R.id.img12);
        img[11]=imageView12;
        final ImageView imageView13 = (ImageView) findViewById(R.id.img13);
        img[12]=imageView13;
        final ImageView imageView14 = (ImageView) findViewById(R.id.img14);
        img[13]=imageView14;
        final ImageView imageView15 = (ImageView) findViewById(R.id.img15);
        img[14]=imageView15;
        final ImageView imageView16 = (ImageView) findViewById(R.id.img16);
        img[15]=imageView16;

          //  String url="https://firebasestorage.googleapis.com/v0/b/cs465menugram.appspot.com/o/m1.jpg?alt=media&token=317d1ea8-bd9b-40cb-a2bd-bb91103acb9c";//Retrieved url as mentioned above
           // Glide.with(getApplicationContext()).load(url).into(imageView1);

            final TextView aTextView = (TextView)findViewById(R.id.addressTextView);
            final TextView tagTextView = (TextView)findViewById(R.id.tagTextView);
            final TextView ratingTextView = (TextView)findViewById(R.id.ratingTextView);

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            Intent intent = getIntent();

            final String restaurantName = intent.getStringExtra("resName");
            MenuActivity.menurn = restaurantName;
            Log.d("n", restaurantName);

        String text = restaurantName;
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(getApplicationContext(), text, duration);
//        toast.show();

            //final String restaurantName = "Sakanaya";

            final ImageView logoIconImageView = (ImageView)findViewById(R.id.logoIcon);

            final TextView restaurantNameTextView = (TextView)findViewById(R.id.restaurantTextView);
            restaurantNameTextView.setText(restaurantName);


            myRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                DataSnapshot newSnapShot = dataSnapshot.child("Logos");
                Iterator<DataSnapshot> snapshotIterator = newSnapShot.getChildren().iterator();

             //   TextView descriptionView = new TextView(SearchActivity.this);

             //   boolean textChanged = false;
                while(snapshotIterator.hasNext()) {
                    DataSnapshot temp_snapshot = snapshotIterator.next();
                    String restaurant_name = temp_snapshot.getKey().toString();
                    if(restaurantName.equals(restaurant_name)) {
                        Glide.with(getApplicationContext()).load(temp_snapshot.getValue()).into(logoIconImageView);
                        break;
                    }
                }

                DataSnapshot newSnapShot1 = dataSnapshot.child("Restaurants");
                Iterator<DataSnapshot> snapshotIterator1 = newSnapShot1.getChildren().iterator();

                while(snapshotIterator1.hasNext()){
                    DataSnapshot temp_snapshot = snapshotIterator1.next();
                    String restaurant_name = temp_snapshot.getKey().toString();
                    if(restaurantName.equals(restaurant_name)){
                        Log.d("w", temp_snapshot.getValue().toString());
                        ParsingClass1 p = temp_snapshot.getValue(ParsingClass1.class);
                        aTextView.setText(p.Address);
                        tagTextView.setText(p.Tag);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.w("SEARCH_ACTIVITY", "searching database FAILED");
            }

        });



            myRef.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    ArrayList<String> a = new ArrayList<>();
                    ArrayList<Double> d = new ArrayList<>();
                    Log.d("here", "hello");

                    DataSnapshot newSnapShot = dataSnapshot.child("uploads"+"/"+restaurantName);
                    Iterator<DataSnapshot> uploads1Iterator = newSnapShot.getChildren().iterator();
                    int i = 0;
                    while(uploads1Iterator.hasNext()){
                        Log.d("2", "hello1");
                        DataSnapshot temp_snapshot = uploads1Iterator.next();
                        Log.d("temp_snapshot", temp_snapshot.toString());
                        ParsingClass p = temp_snapshot.getValue(ParsingClass.class);
                        Log.d("hello3", p.toString());
                        a.add(p.url);
                        d.add(p.rating);
                        Log.d("logging url", p.url);
                        String description = temp_snapshot.getValue().toString();
                    }

                    for(int k = 0; k<a.size() && k < 16; k++){
                            String s = a.get(k);
                            Log.d("s", s);
                            Glide.with(getApplicationContext()).load(s).into(img[k]);
                    }


                        double sum = 0;
                        for (int j = 0; j < d.size(); j++) {
                            sum = sum + d.get(j);
                        }
                        double avg = sum / d.size();
                        String str = String.valueOf(avg);
                        ratingTextView.setText(str);


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
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
        } else if (v==camera) {
            Intent intent = new Intent(this, ReviewActivity.class);
            startActivity(intent);
        }

    }
//
//    private void setupBottomNavigationView(){
//        com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
//        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
//        BottomNavigationViewHelper.enableNavigation(RestaurantActivity.this, this,bottomNavigationViewEx);
//        Menu menu = bottomNavigationViewEx.getMenu();
//        MenuItem menuItem = menu.getItem(3);
//        menuItem.setChecked(true);
//    }
}
