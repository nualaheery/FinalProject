package com.example.finalproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;

public class ChangePinNumberNew extends AppCompatActivity {

    private PinEntryEditText pinEditText;
    private PinEntryEditText pinConfirmEditText;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin_number_new);

        pinEditText = findViewById(R.id.pinEditText2);
        pinConfirmEditText = findViewById(R.id.pinConfirmEditText2);
        submitBtn = findViewById(R.id.submitBtn2);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((pinEditText.getText().toString().equals(pinConfirmEditText.getText().toString())) && (pinEditText.getText().toString() != null
                        && pinConfirmEditText.getText().toString() != null)) {
                    Intent intent = new Intent (ChangePinNumberNew.this, GamePlayActivity.class);
                    intent.putExtra("chosenpin", pinEditText.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(ChangePinNumberNew.this, "Pins do not match, try again!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
