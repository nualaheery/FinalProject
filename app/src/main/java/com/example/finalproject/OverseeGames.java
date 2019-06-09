package com.example.finalproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverseeGames extends Fragment implements View.OnClickListener {

    CardView pauseCard;
    CardView resumeCard;
    CardView stopCard;

    public OverseeGames() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_oversee_games, container, false);

        pauseCard = view.findViewById(R.id.pauseCard);
        resumeCard = view.findViewById(R.id.resumeCard);
        stopCard = view.findViewById(R.id.stopCard);

        pauseCard.setOnClickListener(this);
        resumeCard.setOnClickListener(this);
        stopCard.setOnClickListener(this);



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pauseCard:
                Toast.makeText(getActivity(),"All games paused",Toast.LENGTH_SHORT).show();
                break;
            case R.id.resumeCard:
                Toast.makeText(getActivity(),"All games resumed",Toast.LENGTH_SHORT).show();
                break;
            case R.id.stopCard:
                Toast.makeText(getActivity(),"All games stopped",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
