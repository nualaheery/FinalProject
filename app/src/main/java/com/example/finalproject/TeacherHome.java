package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.OverScroller;

public class TeacherHome extends AppCompatActivity implements View.OnClickListener {

    CardView codesCard;
    CardView currentGamesCard;
    CardView questionsCard;
    CardView playingcardsCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);

        codesCard = findViewById(R.id.gameCodesCard);
        currentGamesCard = findViewById(R.id.currentGamesCard);
        questionsCard = findViewById(R.id.questionsCard);
        playingcardsCard = findViewById(R.id.playingCardsCard);


        codesCard.setOnClickListener(this);
        currentGamesCard.setOnClickListener(this);
        questionsCard.setOnClickListener(this);
        playingcardsCard.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gameCodesCard:
                GameCodesFragment gameCodesFragment = new GameCodesFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_teacher, gameCodesFragment).addToBackStack(null).commit();
                break;
            case R.id.currentGamesCard:
                OverseeGames overseeGamesFragment = new OverseeGames();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_teacher,overseeGamesFragment,null).addToBackStack(null).commit();
                break;
            case R.id.questionsCard:
               Intent intent = new Intent(this,Questions.class);
               startActivity(intent);
                break;
            case R.id.playingCardsCard:
                Intent intent2 = new Intent(this,AddPlayingCards.class);
                startActivity(intent2);
                break;
        }
    }
}
