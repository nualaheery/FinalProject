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

public class GreenCardAdapter extends RecyclerView.Adapter<GreenCardAdapter.GreenCardViewHolder>{



    private Context mContext;
    private ArrayList<PlayingCard> mCardList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public GreenCardAdapter(Context context, ArrayList<PlayingCard> cardList) {
        mContext = context;
        mCardList = cardList;
    }

    @NonNull
    @Override
    public GreenCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.greencards, viewGroup,false);
        return new GreenCardViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GreenCardViewHolder greenCardViewHolder, int i) {

        PlayingCard currentItem = mCardList.get(i);

        String caption = currentItem.getmCaption();
        int amount = currentItem.getmAmount();

        greenCardViewHolder.greenCaptionTextView.setText(caption);
        greenCardViewHolder.greenAmountTextView.setText("£" +String.valueOf(amount));
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public class GreenCardViewHolder extends RecyclerView.ViewHolder {


        public TextView greenCaptionTextView;
        public TextView greenAmountTextView;
        public Button mRemoveBtn;

        public GreenCardViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            greenCaptionTextView = itemView.findViewById(R.id.greencaptiontextview);
            greenAmountTextView = itemView.findViewById(R.id.greenamounttextview);
            mRemoveBtn = itemView.findViewById(R.id.removegreenBtn);

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
