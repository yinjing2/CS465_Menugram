package com.example.cs465_menugram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.w3c.dom.Text;

import java.util.Iterator;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView restaurantNameTextView;
    private TextView getRestaurantDescriptionTextView;
    private LinearLayout resultsListView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        setupBottomNavigationView();

        //Firebase Realtime Database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        restaurantNameTextView = (TextView)findViewById(R.id.text_view_search_activity);
        restaurantNameTextView.setOnClickListener(SearchActivity.this);

        resultsListView = (LinearLayout) findViewById(R.id.linear_layout_search_activity);
        searchView = (SearchView) findViewById(R.id.search_view_search_activity);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                    myRef.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        DataSnapshot restaurantSnapShot = dataSnapshot.child("Restaurants");
                        Iterator<DataSnapshot> restaurantIterator = restaurantSnapShot.getChildren().iterator();

                        boolean textChanged = false;
                        while(restaurantIterator.hasNext()) {
                            DataSnapshot temp_snapshot = restaurantIterator.next();
                            String restaurant_name = temp_snapshot.getKey();
                            String description = temp_snapshot.getValue().toString();

                            if(query.equals(temp_snapshot.getKey())) {
                                restaurantNameTextView.setText(restaurant_name);
                                TextView descriptionView = new TextView(SearchActivity.this);
                                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                descriptionView.setText(temp_snapshot.getValue().toString());
                                resultsListView.addView(descriptionView);
                                textChanged = true;
                                break;
                            }
                        }
                        if(!textChanged) {
                            restaurantNameTextView.setText("No restaurants found");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Log.w("SEARCH_ACTIVITY", "searching database FAILED");
                        restaurantNameTextView.setText("No restaurants found");
                    }

                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
            // do something when text changes
                return false;
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.text_view_search_activity) {
            restaurantNameTextView = (TextView)findViewById(R.id.text_view_search_activity);
            if(restaurantNameTextView.getText().equals("McDonalds")) {
                Intent intent = new Intent(this, RestaurantActivity.class);
                startActivity(intent);
            }

        }
    }

    private void setupBottomNavigationView(){
        com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(SearchActivity.this, this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }
}
