package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CashPaymentDialog extends AppCompatDialogFragment {

    private Player player = GamePlayActivity.player;
    private int amount = GamePlayActivity.amount;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.cash_payment_dialog, null);

        builder.setView(view)
                .setTitle("Paying by cash")
                .setMessage("Are you sure you want to pay by cash?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
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
            final Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int amountOwed = amount;
                    player.setCashAmount(player.getCashAmount() - amountOwed);

                    Toast.makeText(getActivity(), "Paid £" + amount + "with cash", Toast.LENGTH_SHORT).show();

                    GamePlayActivity.paid = true;



                }
            });
        }

    }

}
