package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class TeacherHome extends AppCompatActivity implements View.OnClickListener {

    CardView startGamesCard;
    CardView playingcardsCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        startGamesCard = findViewById(R.id.currentGamesCard);
        playingcardsCard = findViewById(R.id.playingCardsCard);

        startGamesCard.setOnClickListener(this);
        playingcardsCard.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.currentGamesCard:
                Intent intent = new Intent(this, GamePlayActivity.class);
                startActivity(intent);
                break;

            case R.id.playingCardsCard:
                Intent intent2 = new Intent(this,AddPlayingCards.class);
                startActivity(intent2);
                break;
        }
    }
}
