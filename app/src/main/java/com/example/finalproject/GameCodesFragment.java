package com.example.finalproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameCodesFragment extends Fragment {

    private Button generateCodesBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_game_codes, container, false);


        generateCodesBtn = view.findViewById(R.id.genereateCodesBtn);
        generateCodesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGameCodes();
            }
        });

        return view;
    }

    public void getGameCodes() {

    }

}
