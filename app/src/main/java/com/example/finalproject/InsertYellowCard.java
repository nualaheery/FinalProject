package com.example.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class InsertYellowCard extends AppCompatActivity {

    private EditText insertCaptionEditText;
    private EditText insertAmountEditText;

    private Button submitYellowBtn;

    private RequestQueue requestQueue;

    private String insertURL = "http://nheery01.lampt.eeecs.qub.ac.uk/playingcards/insertyellowcard.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_yellow_card);

        insertCaptionEditText = findViewById(R.id.yellowCaptionEditText);
        insertAmountEditText = findViewById(R.id.yellowAmountEditText);
        submitYellowBtn = findViewById(R.id.submityellowCardBtn);


        requestQueue = Volley.newRequestQueue(getApplicationContext());

        submitYellowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertYellowCard();
            }
        });


    }

    private void insertYellowCard() {
        StringRequest request = new StringRequest(Request.Method.POST, insertURL, new Response.Listener<String>() {
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
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("caption",insertCaptionEditText.getText().toString());
                parameters.put("amount",insertAmountEditText.getText().toString());

                return parameters;
            }
        };
        requestQueue.add(request);
    }
}
