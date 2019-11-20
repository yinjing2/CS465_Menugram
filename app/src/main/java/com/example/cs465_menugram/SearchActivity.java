package com.example.cs465_menugram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private TextView textView;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        setupBottomNavigationView();

        //Firebase Realtime Database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();

        textView = (TextView)findViewById(R.id.text_view_search);

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
//                Log.d("SEARCH_ACTIVITY", "Value is: " + map);
//                textView.setText(map.toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.w("SEARCH_ACTIVITY", "Failed to read value. ", databaseError.toException());
//            }
//        });


        searchView = (SearchView) findViewById(R.id.search_view_search_activity);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {

              //  DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
             //   Query result = mFirebaseDatabaseReference.child("userTasks").orderByChild("title").equalTo("#" + query);
                        myRef.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                        Log.d("SEARCH_ACTIVITY", "Value is: " + map);
                        Log.d("SEARCH_ACTIVITY", map.get(query).toString());

                        textView.setText(map.get(query).toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        Log.w("SEARCH_ACTIVITY", "searching database FAILED");
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

    private void setupBottomNavigationView(){
        com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(SearchActivity.this, this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }
}
