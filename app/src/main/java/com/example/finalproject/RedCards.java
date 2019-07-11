package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RedCards extends AppCompatActivity implements RedCardAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RedCardAdapter mRedCardAdapter;
    private ArrayList<PlayingCard> mPlayingCardList;
    private RequestQueue mRequestQueue;


    private Button addRedCardBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_cards);

        mRecyclerView = findViewById(R.id.recycler_view_redcards);
        //mRecyclerView.hasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        mPlayingCardList = new ArrayList<>();

        addRedCardBtn = findViewById(R.id.addRedCardsBtn);
        addRedCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInsertRedCard();
            }
        });

        mRequestQueue = Volley.newRequestQueue(this);
        parseRedJSON();

    }

    private void parseRedJSON() {
        final String redURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/redcards2.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, redURL, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("redcards");

                            for (int i=0; i<jsonArray.length(); i++) {
                                JSONObject redCard = jsonArray.getJSONObject(i);

                                String caption = redCard.getString("caption");
                                int amount = redCard.getInt("amount");

                                mPlayingCardList.add(new PlayingCard(caption,amount));
                            }


                            mRedCardAdapter = new RedCardAdapter(RedCards.this, mPlayingCardList);
                            mRecyclerView.setAdapter(mRedCardAdapter);

                           mRedCardAdapter.setOnItemClickListener(RedCards.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    public void goToInsertRedCard() {
        Intent intent = new Intent(this, InsertRedCard.class);
        startActivity(intent);
    }


    @Override
    public void onRemoveClick(int position) {

        PlayingCard clickedCard = mPlayingCardList.get(position);
        final String itemToRemove = clickedCard.getmCaption();
         String removeCardFromGameURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/removecardfromgame.php";

            StringRequest request = new StringRequest(Request.Method.POST, removeCardFromGameURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("caption", itemToRemove);
                    Log.d("nuala", "card removed: " +itemToRemove);


                    return parameters;
                }
            };

            mRequestQueue.add(request);
        mRedCardAdapter.notifyItemRemoved(position);
    }
}

