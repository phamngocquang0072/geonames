package com.example.country_new;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import pl.droidsonroids.gif.GifImageView;

public class DetailActivity extends AppCompatActivity {

    private static final String GEONAME_API_KEY= "ngotruongkhai";
    private ProgressBar progressBar;
    private GifImageView ivNation;
    private TextView tvNameCountry;
    private TextView tvArea;
    private TextView tvPopulation;
    private GifImageView ivFlag;
    private CountryClient client;
    private Button pre, exit, next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        MainActivity main = new MainActivity();
        // Fetch views
        ivNation = (GifImageView) findViewById(R.id.ivNation);
        tvNameCountry = (TextView) findViewById(R.id.tvCoutryName);
        tvArea = (TextView) findViewById(R.id.tvArea);
        tvPopulation = (TextView) findViewById(R.id.tvPopulation);
        ivFlag = (GifImageView) findViewById(R.id.ivFlag);

        pre = (Button) findViewById(R.id.pre);
        exit = (Button) findViewById(R.id.exit);
        next = (Button) findViewById(R.id.next);

        String coutryCode = getIntent().getStringExtra(MainActivity.COUNTRY_DETAIL_KEY);
        final int[] pos = {0};
        pos[0] = getIntent().getIntExtra(MainActivity.COUNTRY_DETAIL_POS,0);
        Log.e("Kiem tra Country Activity", coutryCode);
        loadCountry(coutryCode);

        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountriesAdapter adapter1 = main.getAdapter();
                Log.e("pos= ", ""+ pos[0]);
                if(pos[0] - 1 < 0){
                    Toast.makeText(DetailActivity.this, "This is the first item!!!",Toast.LENGTH_LONG);
                }else{
                    String newCode = adapter1.getItem(pos[0] -1).getCountryCode();
                    loadCountry(newCode);
                    pos[0] -=1;
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CountriesAdapter adapter1 = main.getAdapter();
                Log.e("pos= ", ""+ pos[0]);
                if(pos[0] + 1 > main.getAdapter().getCount()){
                    Toast.makeText(DetailActivity.this, "This is the last item!!!",Toast.LENGTH_LONG);
                }else{
                    String newCode = adapter1.getItem(pos[0] +1).getCountryCode();
                    loadCountry(newCode);
                    pos[0] +=1;
                }
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(intent2);
            }
        });

    }



    // Populate data for the book
    private void loadCountry(String countryCode) {

        // fetch extra book data from books API
        client = new CountryClient();
        client.getExtraCountryDetails(GEONAME_API_KEY, countryCode, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray docs = null;
                    if(response.has("geonames")) {
                        Log.e("Kiem tra Country Activity", response.toString());
                        // Get the docs json array
                        docs = response.getJSONArray("geonames");
                        // Parse json array into array of model objects
                        final ArrayList<Country> countries = Country.fromJson(docs);

                        //change activity title
                        Log.e("Kiem tra Country Activity", countries.get(0).getCountryName());
                        DetailActivity.this.setTitle(countries.get(0).getCountryName());
                        // Populate data
                        String nationUrl = "https://img.geonames.org/img/country/250/"+ countries.get(0).getCountryCode().toUpperCase() + ".png";
                        Log.e("Kiem tra Country Activity", nationUrl);
                        Picasso.with(getApplicationContext())
                                .load(nationUrl)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.stub)
                                .into(ivNation);
                        tvNameCountry.setText(countries.get(0).getCountryName());
                        tvArea.setText(countries.get(0).getAreaInSqKm() + " km²");
                        tvPopulation.setText(Integer.toString(countries.get(0).getPopulation()) + " people");
                        String flagUrl = "https://img.geonames.org/flags/x/"+ countries.get(0).getCountryCode().toLowerCase() + ".gif";
                        Log.e("Kiem tra Country Activity", flagUrl);
                        Picasso.with(getApplicationContext())
                                .load(flagUrl)
                                .placeholder(R.drawable.ic_launcher_foreground)
                                .error(R.drawable.stub)
                                .into(ivFlag);
                    }
                } catch (JSONException e) {
                    // Invalid JSON format, show appropriate error.
                    e.printStackTrace();
                }
            }
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "Load Failure", Toast.LENGTH_SHORT).show();
            }
        });


    }
}