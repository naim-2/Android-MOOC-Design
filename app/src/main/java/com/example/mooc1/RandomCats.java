package com.example.mooc1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RandomCats extends AppCompatActivity {

    private ImageView imageViewCat;
    private ProgressBar progressBar;
    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_cats);

        // Initialize views
        imageViewCat = findViewById(R.id.image_view_cat);
        progressBar = findViewById(R.id.progress_bar);
        myBroadcastReceiver = new MyBroadcastReceiver();

        // Register the broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.mooc1.CUSTOM_ACTION");
        registerReceiver(myBroadcastReceiver, filter);

        // Send a broadcast message
        Intent intent = new Intent("com.example.mooc1.CUSTOM_ACTION");
        sendBroadcast(intent);

        // Make a network request in background thread
        new NetworkTask().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the broadcast receiver
        unregisterReceiver(myBroadcastReceiver);
    }

    // AsyncTask to perform network request in the background
    private class NetworkTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show progress bar
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            HttpURLConnection urlConnection = null;
            try {
                // Create a URL object
                URL url = new URL("https://api.thecatapi.com/v1/images/search");
                // Open a connection to the URL
                urlConnection = (HttpURLConnection) url.openConnection();
                // Get input stream from the connection
                InputStream in = urlConnection.getInputStream();
                // Create a buffered reader to read from the input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                // Read each line from the input stream and append to the string builder
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                // Convert string builder to string
                result = stringBuilder.toString();
            } catch (IOException e) {
                // Log error message if an exception occurs
                Log.e("RandomCats", "Error fetching data: " + e.getMessage());
            } finally {
                // Disconnect the URL connection
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            // Return the result
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Hide progress bar
            progressBar.setVisibility(View.GONE);

            // Parse JSON response
            try {
                // Create a JSONArray from the result string
                JSONArray jsonArray = new JSONArray(result);
                if (jsonArray.length() > 0) {
                    // Get the first JSONObject from the array
                    JSONObject countryObject = jsonArray.getJSONObject(0);
                    // Get the value of the "url" key from the object
                    String imageUrl = countryObject.getString("url");

                    // Load the image into the ImageView using Picasso library
                    Picasso.get().load(imageUrl).into(imageViewCat);
                    // Make the ImageView visible
                    imageViewCat.setVisibility(View.VISIBLE);
                } else {
                    // Log error message if no cat image is found
                    Log.e("RandomCats", "No cat image found");
                }
            } catch (JSONException e) {
                // Log error message if JSON parsing fails
                Log.e("RandomCats", "Error parsing JSON: " + e.getMessage());
            }
        }
    }

    // BroadcastReceiver to receive custom action
    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Handle broadcast message here
            Log.d("RandomCats", "Received broadcast message");
            // For example, you can refresh the cat image by executing the network request again
            new NetworkTask().execute();
        }
    }
}
