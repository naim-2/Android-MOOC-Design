package com.example.mooc1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

// Define the HomePage class extending AppCompatActivity
public class HomePage extends AppCompatActivity {

    // Called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the activity
        setContentView(R.layout.activity_home_page);

        // Find the button for launching MainActivity in the layout
        Button buttonMainActivity = findViewById(R.id.button_main_activity);
        // Set a click listener for the button
        buttonMainActivity.setOnClickListener(new View.OnClickListener() {
            // Called when the button is clicked
            @Override
            public void onClick(View v) {
                // Create an intent to launch CountrySearch activity
                Intent intent = new Intent(HomePage.this, CountrySearch.class);
                // Start the CountrySearch activity
                startActivity(intent);
            }
        });

        // Find the button for launching MainActivity2 in the layout
        Button buttonMainActivity2 = findViewById(R.id.button_main_activity_2);
        // Set a click listener for the button
        buttonMainActivity2.setOnClickListener(new View.OnClickListener() {
            // Called when the button is clicked
            @Override
            public void onClick(View v) {
                // Create an intent to launch RandomCats activity
                Intent intent = new Intent(HomePage.this, RandomCats.class);
                // Start the RandomCats activity
                startActivity(intent);
            }
        });
    }
}
