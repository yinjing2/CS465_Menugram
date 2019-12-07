package com.example.cs465_menugram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.algolia.search.saas.AlgoliaException;
import com.algolia.search.saas.Client;
import com.algolia.search.saas.Query;
import com.algolia.search.saas.CompletionHandler;
import com.algolia.search.saas.Index;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView restaurantNameTextView;
    private LinearLayout resultsListView;



    private LinearLayout resultsLinearLayout;
    private SearchView searchView;

    //Algolia
    private Client client;
    private Index index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        setupBottomNavigationView();

        //Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        //Algolia
        client = new Client("65RNJE1MRH", "be0adeb52e168e450b61fc1ad321fce3");
        index = client.getIndex("dev_Restaurants");

        restaurantNameTextView = (TextView)findViewById(R.id.text_view_search_activity);
        restaurantNameTextView.setOnClickListener(SearchActivity.this);

        resultsListView = (LinearLayout) findViewById(R.id.linear_layout_search_activity);
        resultsLinearLayout = (LinearLayout) findViewById(R.id.linear_layout_1_search_activity);
        searchView = (SearchView) findViewById(R.id.search_view_search_activity);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
            // do algolia async search
                Index index = client.getIndex("dev_Restaurants");

                Log.d("SEARCH_ACTIVITY", "submitted" + query);

                Query algolia_query = new Query(query).setHitsPerPage(50);
                index.searchAsync(algolia_query, new CompletionHandler() {
                    @Override
                    public void requestCompleted(JSONObject content, AlgoliaException error) {
                        // [...]
                        Log.d("SEARCH_ACTIVITY", content.toString());
                    }
                });

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
            // do algolia async search
                resultsLinearLayout.removeAllViews();
                Index index = client.getIndex("dev_Restaurants");

                Log.d("SEARCH_ACTIVITY", "submitted" + newText);

                Query algolia_query = new Query("McDonalds");
                index.searchAsync(algolia_query, new CompletionHandler() {
                    @Override
                    public void requestCompleted(JSONObject content, AlgoliaException error) {
                        // [...]

                        //Get array of results
                        JSONArray objects = new JSONArray();
                        JSONObject results = new JSONObject();

                        try {
                            objects = content.getJSONArray("hits");
                            results = objects.getJSONObject(0);
                        } catch (JSONException e) {
                            Log.d("SEARCH_ACTIVITY", "JSON EXCEPTION");
                        }

                        Iterator<String> keys = results.keys();
                        while(keys.hasNext()) {
                            String key = keys.next();
                            Log.d("SEARCH_ACTIVITY", "key is " + key);

                            if(key.equals("McDonalds")) {
                                ImageButton img = new ImageButton(SearchActivity.this);
                                img.setImageResource(R.drawable.mcdonalds_search);
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 620));


                                //FIX THIS
                                img.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(SearchActivity.this, RestaurantActivity.class);
                                        startActivity(intent);
                                    }
                                });



                                resultsLinearLayout.addView(img);
                            }

                            else if(key.equals("Sakanaya")) {
                                ImageButton img = new ImageButton(SearchActivity.this);
                                img.setImageResource(R.drawable.sakanaya_search);
                                img.setScaleType(ImageView.ScaleType.FIT_XY);
                                img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 620));
                                resultsLinearLayout.addView(img);
                            }

                        }

                    }
                });

                return true;
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.text_view_search_activity) {
            restaurantNameTextView = (TextView)findViewById(R.id.text_view_search_activity);
            Intent intent = new Intent(this, RestaurantActivity.class);
            intent.putExtra("resName", restaurantNameTextView.getText());
            startActivity(intent);

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
