package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class PinNumber extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_number);

        MonsterItem playersMonster = MonsterDetails.monsterItem;



        String imageName = playersMonster.getMonsterImageResouce();
        int imageResID = PinNumber.this.getResources().getIdentifier(imageName, "drawable", getPackageName());

        String monsterName = playersMonster.getNameText();

        ImageView monsterImage = findViewById(R.id.imageTest);
        monsterImage.setImageResource(imageResID);
    }
}
