package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RepayCreditWithCashDialog extends AppCompatDialogFragment {

    private EditText amountChosenToRepay;
    private double totalCreditAmount =  GamePlayActivity.player.getCreditAmount();
    private double totalCashAmount = GamePlayActivity.player.getCashAmount();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.repay_credit_dialog, null);
        amountChosenToRepay = view.findViewById(R.id.amountToRepay);


        builder.setView(view)
                .setTitle("Repay your credit")
                .setMessage("Please enter how much you would like to pay")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            final Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String amountEnteredString = amountChosenToRepay.getText().toString();
                    double amountEntered = Double.parseDouble(amountEnteredString);

                    Boolean wantToCloseDialog = false;

                    if (amountEntered > (totalCreditAmount *-1) ){ //make credit amount positive for comparison
                        Toast.makeText(getActivity(), "Value entered is too high", Toast.LENGTH_SHORT).show();
                    } else if (amountEntered < 0) {
                        Toast.makeText(getActivity(), "Value cannot be less than 0", Toast.LENGTH_SHORT).show();
                    } else if (amountEntered > totalCashAmount) {
                        Toast.makeText(getActivity(), "Not enough funds", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //updating credit value
                        GamePlayActivity.player.setCreditAmount(totalCreditAmount + amountEntered);
                        //updating debit value
                        GamePlayActivity.player.setCashAmount(totalCashAmount - amountEntered);
                        wantToCloseDialog = true;

                    }

                    if (wantToCloseDialog) {
                        d.dismiss();
                    }

                }
            });




        }
    }
}
