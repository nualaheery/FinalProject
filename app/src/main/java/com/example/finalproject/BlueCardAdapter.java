package com.example.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class BlueCardAdapter extends  RecyclerView.Adapter<BlueCardAdapter.BlueCardViewHolder>{

    private Context mContext;
    private ArrayList<PlayingCard> mCardList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public BlueCardAdapter(Context context, ArrayList<PlayingCard> cardList) {
        mContext = context;
        mCardList = cardList;
    }

    @NonNull
    @Override
    public BlueCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.bluecards, viewGroup,false);
        return new BlueCardViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BlueCardViewHolder blueCardViewHolder, int i) {
        PlayingCard currentItem = mCardList.get(i);

        String caption = currentItem.getmCaption();
        int amount = currentItem.getmAmount();

        blueCardViewHolder.blueCaptionTextView.setText(caption);
        blueCardViewHolder.blueAmountTextView.setText(String.valueOf(amount));

    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public class BlueCardViewHolder extends  RecyclerView.ViewHolder {


        public TextView blueCaptionTextView;
        public TextView blueAmountTextView;
        public Button mRemoveBtn;

        public BlueCardViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            blueCaptionTextView = itemView.findViewById(R.id.bluecaptiontextview);
            blueAmountTextView = itemView.findViewById(R.id.blueamounttextview);
            mRemoveBtn = itemView.findViewById(R.id.removeblueBtn);

            mRemoveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRemoveClick(position);
                        }
                    }
                }
            });
        }
    }
}
