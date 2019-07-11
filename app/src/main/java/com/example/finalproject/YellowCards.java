package com.example.finalproject;

import android.app.Activity;
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

public class YellowCards extends AppCompatActivity implements YellowCardAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private YellowCardAdapter mYellowCardAdapter;
    private ArrayList<PlayingCard> mCardList;
    private RequestQueue mRequestQueue;
    private Activity mActivity = YellowCards.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow_cards);

        mRecyclerView = findViewById(R.id.yelliw_recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        mCardList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        loadAllYellowCards();


        Button addYellowCardBtn = findViewById(R.id.addyellowBtn);

        addYellowCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YellowCards.this,InsertYellowCard.class);
                startActivity(intent);
            }
        });

    }

    private void loadAllYellowCards() {
        String URL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/yellowcards.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("yellowcards");

                            for (int i=0; i< jsonArray.length(); i++) {
                                JSONObject yellowCard = jsonArray.getJSONObject(i);

                                String caption = yellowCard.getString("caption");
                                int amount = yellowCard.getInt("amount");

                                mCardList.add(new PlayingCard(caption,amount));
                            }

                            mYellowCardAdapter = new YellowCardAdapter(YellowCards.this,mCardList);
                            mRecyclerView.setAdapter(mYellowCardAdapter);

                            mYellowCardAdapter.setOnItemClickListener(YellowCards.this);

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
    public void onRemoveClick(int position) {
        PlayingCard clickedCard = mCardList.get(position);
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
        mYellowCardAdapter.notifyItemRemoved(position);
        mRequestQueue.add(request);

        Toast.makeText(mActivity, "Card removed", Toast.LENGTH_SHORT).show();
      //  mActivity.recreate();

     //   mCardList.clear();
    }
}
