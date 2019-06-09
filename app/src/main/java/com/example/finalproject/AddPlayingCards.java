package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddPlayingCards extends AppCompatActivity implements View.OnClickListener {

    private Button viewRed;
    private Button viewyellow;
    private Button viewBlue;
    private Button viewGreen;
    private Button viewRemovedCards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playing_cards);

        viewRed = findViewById(R.id.viewRedBtn);
        viewyellow = findViewById(R.id.viewYellowBtn);
        viewBlue = findViewById(R.id.viewBlueBtn);
        viewGreen = findViewById(R.id.viewGreenBtn);
        viewRemovedCards = findViewById(R.id.seeRemovedCardsBtn);

        viewRed.setOnClickListener(this);
        viewyellow.setOnClickListener(this);
        viewBlue.setOnClickListener(this);
        viewGreen.setOnClickListener(this);
        viewRemovedCards.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewRedBtn:
                Intent intent = new Intent(this,RedCards.class);
                startActivity(intent);
                break;
            case R.id.viewYellowBtn:
                Intent intent2 = new Intent(this,YellowCards.class);
                startActivity(intent2);
                break;
            case R.id.viewBlueBtn:
                Intent intent3 = new Intent(this,BlueCards.class);
                startActivity(intent3);
                break;
            case R.id.viewGreenBtn:
                Intent intent4 = new Intent(this,GreenCards.class);
                startActivity(intent4);
                break;
            case R.id.seeRemovedCardsBtn:
                Intent intent5 = new Intent(this, ViewRemovedCards.class);
                startActivity(intent5);
                break;
        }
    }
}
