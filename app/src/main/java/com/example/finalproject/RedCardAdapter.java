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

public class RedCardAdapter extends RecyclerView.Adapter<RedCardAdapter.RedCardViewHolder> {

    private Context mContext;
    private ArrayList<PlayingCard> mPlayingCardList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onRemoveClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public RedCardAdapter(Context context, ArrayList<PlayingCard> playingCardList) {
        mContext = context;
        mPlayingCardList = playingCardList;
    }

    @NonNull
    @Override
    public RedCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.redcards, viewGroup,false);
        return new RedCardViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RedCardViewHolder redCardViewHolder, int i) {
        PlayingCard currentItem = mPlayingCardList.get(i);

        String caption = currentItem.getmCaption();
        int amount = currentItem.getmAmount();

        redCardViewHolder.mCaptionText.setText(caption);
        redCardViewHolder.mAmountText.setText("£" +String.valueOf(amount));

    }

    @Override
    public int getItemCount() {
        return mPlayingCardList.size();
    }

    public static class RedCardViewHolder extends RecyclerView.ViewHolder {

        public TextView mCaptionText;
        public TextView mAmountText;
        public Button mRemoveBtn;

        public RedCardViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mCaptionText = itemView.findViewById(R.id.captiontextview);
            mAmountText = itemView.findViewById(R.id.amounttextview);
            mRemoveBtn = itemView.findViewById(R.id.removeRedBtn);

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
