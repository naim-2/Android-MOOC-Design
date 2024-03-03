package com.example.mooc1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CountrySearch extends AppCompatActivity {

    private TextView textViewResult;
    private ProgressBar progressBar;
    private TextInputEditText editTextCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout for the activity
        setContentView(R.layout.activity_country_search);

        // Initialize views
        textViewResult = findViewById(R.id.text_view_result);
        progressBar = findViewById(R.id.progress_bar);
        editTextCountry = findViewById(R.id.edit_text_country);

        // Set a click listener for the fetch button
        findViewById(R.id.button_fetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the country name entered by the user
                String countryName = editTextCountry.getText().toString().trim();
                // If the country name is not empty, initiate a network request
                if (!countryName.isEmpty()) {
                    new NetworkTask().execute(countryName);
                }
            }
        });
    }

    // AsyncTask to perform network operations in the background
    public class NetworkTask extends AsyncTask<String, Void, String> {

        // Called before starting the background thread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Show progress bar
            progressBar.setVisibility(View.VISIBLE);
        }

        // Background thread to perform network operations
        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            HttpURLConnection urlConnection = null;
            try {
                // Retrieve the country name from the parameter
                String countryName = strings[0];
                // Create URL object for the API endpoint
                URL url = new URL("https://restcountries.com/v3.1/name/" + countryName.replace(" ", "%20"));
                // Open connection to the URL
                urlConnection = (HttpURLConnection) url.openConnection();
                // Get input stream from the connection
                InputStream in = urlConnection.getInputStream();
                // Read the input stream
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                // Store the result
                result = stringBuilder.toString();
            } catch (IOException e) {
                // Log error if fetching data fails
                Log.e("CountrySearch", "Error fetching data: " + e.getMessage());
            } finally {
                // Disconnect the URL connection
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            // Return the result
            return result;
        }

        // Called after the network operation is completed
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Hide progress bar
            progressBar.setVisibility(View.GONE);

            // Parse JSON response
            try {
                // Create JSON array from the result
                JSONArray jsonArray = new JSONArray(result);
                // Check if the array is not empty
                if (jsonArray.length() > 0) {
                    // Get the first country object
                    JSONObject countryObject = jsonArray.getJSONObject(0);
                    // Get the name object from the country object
                    JSONObject nameObject = countryObject.getJSONObject("name");
                    // Get the common name and official name from the name object
                    String commonName = nameObject.optString("common");
                    String officialName = nameObject.optString("official");

                    // Set the common name and official name in the text view
                    textViewResult.setText("Common Name: " + commonName + "\n" +
                            "Official Name: " + officialName);
                    textViewResult.setVisibility(View.VISIBLE);
                } else {
                    // Display error message if country does not exist
                    textViewResult.setText("Country not found");
                    textViewResult.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                // Log error if parsing JSON fails
                Log.e("CountrySearch", "Error parsing JSON: " + e.getMessage());
                // Display error message
                textViewResult.setText("Error! Either country not found or check your internet connection!");
                textViewResult.setVisibility(View.VISIBLE);
            }
        }
    }
}
