package com.example.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class PlayingCards extends AppCompatActivity {

    LinearLayout galleryLayout;
    RequestQueue requestQueue;

    TextView captionTextView;
    TextView amountTextView;

    LayoutInflater inflater;


    String showRedURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/redcards2.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_cards);

        captionTextView = findViewById(R.id.getCardCaptions);
        amountTextView = findViewById(R.id.getCardAmount);

        galleryLayout = findViewById(R.id.galleryRed);
        inflater = LayoutInflater.from(this);


        requestQueue = Volley.newRequestQueue(getApplicationContext());



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                showRedURL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                View view = inflater.inflate(R.layout.playingcard, galleryLayout,false);
                try {
                    JSONArray redcards = response.getJSONArray("redcards");
                    for (int i =0; i<redcards.length(); i++) {


                        JSONObject redCard = redcards.getJSONObject(i);

                        String caption = redCard.getString("caption");
                        int amount = redCard.getInt("amount");

                        Log.d("nuala",response.toString());
                        Log.d("nuala",redCard.toString());
                        Log.d("nuala",caption);
                        Log.d("nuala", String.valueOf(amount));

                   //     Log.d("nuala",captionTextView.getText().toString());

                      //  captionTextView.setText(caption);
                        amountTextView.setText(String.valueOf(amount));

                        Log.d("nuala",captionTextView.getText().toString());

                        galleryLayout.addView(view);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
