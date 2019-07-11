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

public class ChangeForgottenPinDialog extends AppCompatDialogFragment {

    private PinEntryEditText pinDebitChangeEntryText;
    private PinEntryEditText pinDebitChangeConfirmEntryText;
    private Player player = GamePlayActivity.player;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.change_pin_dialog, null);
        pinDebitChangeEntryText = view.findViewById(R.id.pinDebitChangeEditText);
        pinDebitChangeConfirmEntryText = view.findViewById(R.id.pinDebitChangeConfirmEditText);


        builder.setView(view)
                .setTitle("Change your pin")
                .setMessage("Enter and confirm your new pin number. You will use this pin number for you debit and credit card from now on")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "cancel clicked",Toast.LENGTH_SHORT).show();
                        GamePlayActivity.paid = false;
                        Log.d("nuala", "boolean after negativeButton: " +GamePlayActivity.paid);
                    }
                })
                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });




        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();
        if (d != null) {
            final Button positiveButton = (Button)d.getButton(Dialog.BUTTON_POSITIVE);
            final Button negativeButton = (Button)d.getButton(Dialog.BUTTON_NEGATIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean wantToCloseDialog = false;
                    String pinEntered = pinDebitChangeEntryText.getText().toString();
                    String correctPin = player.getPinNumber();

                    positiveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Boolean wantToClose = false;

                            if ((pinDebitChangeEntryText.getText().toString().equals(pinDebitChangeConfirmEntryText.getText().toString()))
                                    && (pinDebitChangeEntryText.getText().toString() != null
                                    && pinDebitChangeConfirmEntryText.getText().toString() != null)) {

                                player.setPinNumber(pinDebitChangeEntryText.getText().toString());
                                Toast.makeText(getActivity(), "Pin updated",Toast.LENGTH_SHORT).show();
                                player.setPinBlocked(false); //pin number no longer blocked
                                wantToClose = true;
                                d.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Pins do not match, try again!",Toast.LENGTH_SHORT).show();
                            }
                        }



                    });

                    negativeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "cancel clicked",Toast.LENGTH_SHORT).show();
                            GamePlayActivity.paid = false;
                            Log.d("nuala", "boolean after negativeButton: " +GamePlayActivity.paid);
                        }
                    });


                    if (wantToCloseDialog) {
                        d.dismiss();
                    }
                }
            });
        }
    }



}
