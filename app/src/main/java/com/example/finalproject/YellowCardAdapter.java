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

public class YellowCardAdapter extends RecyclerView.Adapter<YellowCardAdapter.YellowCardViewHolder> {

    private Context mContext;
    private ArrayList<PlayingCard> mCardList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public YellowCardAdapter(Context context, ArrayList<PlayingCard> cardList) {
        mContext = context;
        mCardList = cardList;
    }

    @NonNull
    @Override
    public YellowCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.yellowcards, viewGroup,false);
        return new YellowCardViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull YellowCardViewHolder yellowCardViewHolder, int i) {
        PlayingCard currentItem = mCardList.get(i);

        String caption = currentItem.getmCaption();
        int amount = currentItem.getmAmount();

        yellowCardViewHolder.yellowCaptionTextView.setText(caption);
        yellowCardViewHolder.yellowAmountTextView.setText("£" +String.valueOf(amount));
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public class YellowCardViewHolder extends  RecyclerView.ViewHolder {

        public TextView yellowCaptionTextView;
        public TextView yellowAmountTextView;
        public Button mRemoveBtn;

        public YellowCardViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            yellowCaptionTextView = itemView.findViewById(R.id.yellowcaptiontextview);
            yellowAmountTextView = itemView.findViewById(R.id.yellowamounttextview);
            mRemoveBtn = itemView.findViewById(R.id.removeYellowBtn);

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
