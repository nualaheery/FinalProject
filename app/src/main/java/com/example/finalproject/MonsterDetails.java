package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Random;

public class MonsterDetails extends AppCompatActivity {

    //when the Intent is up here, i get a NullPointerException - how can i pass the MonsterItem onto the next activtiy??

    public static MonsterItem monsterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster_details);

        Intent intent = getIntent();
        monsterItem = intent.getParcelableExtra("Monster Item");

        String imageName = monsterItem.getMonsterImageResouce();
        int imageResID = MonsterDetails.this.getResources().getIdentifier(imageName, "drawable", getPackageName());

        String monsterName = monsterItem.getNameText();
        String debitCardNumber = monsterItem.getDebitCardNumber();
        String creditCardNumber = monsterItem.getCreditCardNumber();


        TextView monsterNameTextView = findViewById(R.id.monsterNewNameText);
        TextView nameOnDebit = findViewById(R.id.debitCardNameTextNew);
        TextView nameOnCredit = findViewById(R.id.creditCardNameTextNew);
        TextView debitCardNumberTxt = findViewById(R.id.debitNumberTextNew);
        TextView creditcardNumberTxt = findViewById(R.id.creditNumberTextNew);
        ImageView monsterImage = findViewById(R.id.monsternewImg);

        monsterNameTextView.setText(monsterName);
        nameOnDebit.setText(monsterName);
        nameOnCredit.setText(monsterName);
        debitCardNumberTxt.setText(debitCardNumber);
        creditcardNumberTxt.setText(creditCardNumber);
        monsterImage.setImageResource(imageResID);

        Button goToPinBtn = findViewById(R.id.pinBtnNew);
        goToPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPinNumber();
            }
        });
    }

    private void goToPinNumber() {
        Intent intent = new Intent(MonsterDetails.this, PinNumberOld.class);
      //  intent.putExtra("monsteritem", monsterItem);
        Random random = new Random();
        String pin = String.format(Locale.getDefault(),"%04d", random.nextInt(10000));
        intent.putExtra("pinNumber",pin);
        startActivity(intent);





    }
}
