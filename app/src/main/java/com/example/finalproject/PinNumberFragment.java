package com.example.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class PinNumberFragment extends Fragment {

    TextView pinTextView;
    Button keepPinBtn;
    Button changePinBtn;
    private String generatedPin;


    public PinNumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pin_number, container, false);
        pinTextView = view.findViewById(R.id.pinTextView);
        keepPinBtn = view.findViewById(R.id.keepPinBtn);
        changePinBtn = view.findViewById(R.id.changePinBtn);

      /*  Random random = new Random();
        String pin = String.format(Locale.getDefault(),"%04d", random.nextInt(10000)); */

    //    pinTextView.setText(pin);

        if (getArguments() !=null) {
            generatedPin = getArguments().getString("generatedPin");


        }

        pinTextView.setText(generatedPin);

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

        return view;


    }

    public void changePin() {
        ChangePinNumber changePinNumber = new ChangePinNumber();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container_characters, changePinNumber, null).addToBackStack(null).commit();


    }

    public void goToGame() {
        //method if player keeps their pin and goes to the game
        Intent intent = new Intent(getActivity(), GamePlayActivity.class);
        startActivity(intent);


    }



}
