package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

public class CreditPayEnterPin extends AppCompatDialogFragment {

    public static final int MAX_LOGIN_ATTEMPT = 2;
    private PinEntryEditText pinCredEntryText;
    private Player player = GamePlayActivity.player;
    private int amount = GamePlayActivity.amount;
    private int loginAttempt;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.pay_credit_dialog, null);
        pinCredEntryText = view.findViewById(R.id.pinCreditEditText);

        loginAttempt = 0;

        builder.setView(view)
                .setTitle("Enter Your Pin")
                .setMessage("Make sure you enter the correct pin")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setNeutralButton("Forgot Pin", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });


        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            final Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            final Button forgotPinButton = d.getButton(Dialog.BUTTON_NEUTRAL);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Boolean wantToCloseDialog = false;
                    String pinEntered = pinCredEntryText.getText().toString(); //pin entered by user
                    String correctPin = player.getPinNumber(); //user's correct pin

                    //checking pin isn't blocked
                    if (loginAttempt < MAX_LOGIN_ATTEMPT) {
                        //checking pin entered is correct
                        if (pinEntered.equals(correctPin)) {
                            int amountOwed = amount; //amount obtained from the specific card
                            player.setCreditAmount(player.getCreditAmount() - amountOwed);

                            Toast.makeText(getActivity(), "Paid £" + amount, Toast.LENGTH_SHORT).show();

                            //setting paid boolean to true
                            GamePlayActivity.paid = true;

                            wantToCloseDialog = true;

                        } else {
                            //pin entered not correct
                            Toast.makeText(getActivity(), "Incorrect pin, try again", Toast.LENGTH_SHORT).show();
                            loginAttempt++;

                        }
                    } else {
                        //setting playerNumber's card to blocked
                        player.setPinBlocked(true);
                        Toast.makeText(getActivity(), "Pin blocked, please create a new pin", Toast.LENGTH_LONG).show();
                        d.dismiss();

                    }
                    if (wantToCloseDialog) {
                        d.dismiss();
                    }
                }
            });

            forgotPinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangeForgottenPinDialog changePinDialog = new ChangeForgottenPinDialog();
                    changePinDialog.show(getActivity().getSupportFragmentManager(), "change pin dialog");
                }
            });


        }

    }
}
