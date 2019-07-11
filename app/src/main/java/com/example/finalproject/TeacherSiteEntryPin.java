package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

public class TeacherSiteEntryPin extends AppCompatDialogFragment {
    
    private PinEntryEditText pin;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.teacher_pin_entry_dialog, null);
        pin = view.findViewById(R.id.pinforTeacherSite);
        
        

        builder.setView(view)
                .setTitle("Access Teacher Site")
                .setMessage("Enter the correct pin to access the teacher site")
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

                    String pinEntered = pin.getText().toString();

                    if (pinEntered.equals("1234")) {
                        Intent intent = new Intent(getActivity(), TeacherHome.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "Incorrect pin, cannot enter teacher site", Toast.LENGTH_SHORT).show();
                    }

                }

            });
        }
    }
}
