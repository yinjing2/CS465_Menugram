package com.example.cs465_menugram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static boolean true_clicked = false;
    public static boolean false_clicked = false;
    private Button reviewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reviewButton = (Button) findViewById(R.id.button);

        reviewButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            true_clicked = true;
            Intent intent = new Intent(this, ReviewActivity.class);
            intent.putExtra("is_True", "true");
            startActivity(intent);
        }
    }
}
