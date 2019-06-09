package com.example.finalproject;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

import static com.example.finalproject.CharactersRecycView.BUNDLE_CREDITCARD_NUMBER;
import static com.example.finalproject.CharactersRecycView.BUNDLE_DEBITCARD_NUMBER;
import static com.example.finalproject.CharactersRecycView.BUNDLE_IMAGE;
import static com.example.finalproject.CharactersRecycView.BUNDLE_NAME;


/**
 * A simple {@link Fragment} subclass.
 */

public class MonsterDetailsFragment extends Fragment implements View.OnClickListener {

    private String name;
    private int imageid;
    private String debitCardNumberText;
    private String creditCardNumberText;
    private Button pinBtn;


    public MonsterDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_monstedetails, container, false);

        TextView nameText = view.findViewById(R.id.monsterNameText);
        TextView nameOnDebCard = view.findViewById(R.id.debitCardNameText);
        TextView nameOnCredCard = view.findViewById(R.id.creditCardNameText);
        TextView debitCardNumber = view.findViewById(R.id.debitNumberText);
        TextView creditCardNumber = view.findViewById(R.id.creditNumberText);
        ImageView monsterImg = view.findViewById(R.id.monster1Img);
        pinBtn = view.findViewById(R.id.pinBtn);



        if (getArguments() !=null) {
            name = getArguments().getString(BUNDLE_NAME);
           imageid = getArguments().getInt(BUNDLE_IMAGE);
           debitCardNumberText = getArguments().getString(BUNDLE_DEBITCARD_NUMBER);
           creditCardNumberText = getArguments().getString(BUNDLE_CREDITCARD_NUMBER);

        }

        nameText.setText(name);
        nameOnDebCard.setText(name);
        nameOnCredCard.setText(name);
        debitCardNumber.setText(debitCardNumberText);
        creditCardNumber.setText(creditCardNumberText);
        Drawable drawable = getResources().getDrawable(imageid );
        monsterImg.setImageDrawable( drawable );


       // monsterImg.setImageDrawable(getResources().getDrawable(imageid));


        pinBtn.setOnClickListener(this);

        return view;
    }




    @Override
    public void onClick(View v) {
        PinNumberFragment pinNumberFragment = new PinNumberFragment();
        Bundle randomPinData = new Bundle();
        //generate random pin
        Random random = new Random();
        String pin = String.format(Locale.getDefault(),"%04d", random.nextInt(10000));



        randomPinData.putString("generatedPin", pin);
        pinNumberFragment.setArguments(randomPinData);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container_characters, pinNumberFragment,null).addToBackStack(null).commit();


    }
}
