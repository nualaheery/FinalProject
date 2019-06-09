package com.example.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

public class ViewRemovedCards extends AppCompatActivity implements ViewRemovedCardsAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private ViewRemovedCardsAdapter mAddRedCardAdapter;
    private ArrayList<PlayingCard> mCardList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_red_cards);

        mRecyclerView = findViewById(R.id.recycler_view_addredcards);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mCardList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);

        showCards();


    }

    private void showCards() {
        String URL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/cardsnotingame.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("cardsnotingame");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject redCard = jsonArray.getJSONObject(i);

                                String caption = redCard.getString("caption");
                                int amount = redCard.getInt("amount");
                                String colour = redCard.getString("colour");

                                mCardList.add(new PlayingCard(caption, amount, colour));
                            }

                            mAddRedCardAdapter = new ViewRemovedCardsAdapter(ViewRemovedCards.this, mCardList);
                            mRecyclerView.setAdapter(mAddRedCardAdapter);

                            mAddRedCardAdapter.setOnItemClickListener(ViewRemovedCards.this);

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



    @Override
    public void onAddClick(int position) {

        PlayingCard clickedCard = mCardList.get(position);
        final String cardToAdd = clickedCard.getmCaption();
        String addCardFromGameURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/addcardtogame.php";

        StringRequest request = new StringRequest(Request.Method.POST, addCardFromGameURL, new Response.Listener<String>() {
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
                parameters.put("caption", cardToAdd);
                Log.d("nuala", "card removed: " + cardToAdd);


                return parameters;
            }
        };
        mAddRedCardAdapter.notifyItemRemoved(position);
        mRequestQueue.add(request);
    }


}
