package com.example.finalproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;



/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePinNumber extends Fragment {

    PinEntryEditText pinEditText;
    PinEntryEditText pinConfirmEditText;
    Button submitBtn;

    public ChangePinNumber() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_pin_number, container, false);
        pinEditText = view.findViewById(R.id.pinEditText);
        pinConfirmEditText = view.findViewById(R.id.pinConfirmEditText);
        submitBtn = view.findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinEditText.getText().toString().equals(pinConfirmEditText.getText().toString())) {
                    Toast.makeText(getActivity(), "Pins Match!",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Pins do not match, try again!",Toast.LENGTH_SHORT).show();
                }
            }
        });




     /*   pinEditText.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                Toast.makeText(getActivity(), ""+pinview.getValue(), Toast.LENGTH_SHORT).show();
            }
        }); */


      /*  if (pinEditText != null) {
            pinEditText.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                @Override
                public void onPinEntered(CharSequence str) {


              /*      if (str.toString().equals(1234)) {
                        Toast.makeText(getActivity(), "SUCCESS", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "FAIL", Toast.LENGTH_SHORT).show();
                        pinEditText.setText(null);

                    }

                }
            });
        } */



        return view;
    }

}
