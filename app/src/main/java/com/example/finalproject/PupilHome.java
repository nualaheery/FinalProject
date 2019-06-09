package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

public class PupilHome extends AppCompatActivity {

    CardView startGameCard;
    CardView howToPlayCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pupil_home);

        startGameCard = (CardView)findViewById(R.id.startGameCard);
        howToPlayCard = (CardView)findViewById(R.id.howToPlayCard);



        startGameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGameSetUp();
            }
        });
        howToPlayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToInstructionsFragment();
            }
        });


    }

    public void goToGameSetUp() {
        Intent intent = new Intent(this,CharactersRecycView.class);
        startActivity(intent);

    }

    public void goToInstructionsFragment() {
        InstructionsFragment instructionsFragment = new InstructionsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home, instructionsFragment).addToBackStack(null).commit();
    }


}
