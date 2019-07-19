package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PinNumber extends AppCompatActivity {

    private TextView pinTextView;
    private Button keepPinBtn;
    private Button changePinBtn;
    public  String generatedPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_number_old);

        pinTextView = findViewById(R.id.pinTextView2);
        keepPinBtn = findViewById(R.id.keepPinBtn2);
        changePinBtn = findViewById(R.id.changePinBtn2);

        Intent intent = getIntent();
        generatedPin = intent.getStringExtra("pinNumber");

        pinTextView.setText(generatedPin);

        keepPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        changePinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePin();
            }
        });

        keepPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame();
            }
        });


        // MonsterItem monsterItem = intent.getParcelableExtra("Monster Item");
    }

    public void changePin() {
       Intent intent = new Intent(PinNumber.this, ChangePinNumber.class);
       startActivity(intent);


    }

    public void goToGame() {
        Intent intent = new Intent(PinNumber.this, GamePlayActivity.class);
        intent.putExtra("chosenpin",generatedPin);
        startActivity(intent);


    }
}
