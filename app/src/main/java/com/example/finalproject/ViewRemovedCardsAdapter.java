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

public class ViewRemovedCardsAdapter extends RecyclerView.Adapter<ViewRemovedCardsAdapter.AddRedCardViewHolder> {

    private Context mContext;
    private ArrayList<PlayingCard> mCardList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ViewRemovedCardsAdapter(Context context, ArrayList<PlayingCard> cardList) {
        mContext = context;
        mCardList = cardList;
    }

    @NonNull
    @Override
    public AddRedCardViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.redcardsadd, viewGroup, false);
        return new AddRedCardViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AddRedCardViewHolder addRedCardViewHolder, int i) {
        PlayingCard currentCard = mCardList.get(i);

        String caption = currentCard.getmCaption();
        int amount = currentCard.getmAmount();
        String colour = currentCard.getmColour();

        addRedCardViewHolder.mCaptionTextView.setText(caption);
        addRedCardViewHolder.mAmountTextView.setText(String.valueOf(amount));
        addRedCardViewHolder.mColourTextView.setText(colour);
    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public static class AddRedCardViewHolder extends RecyclerView.ViewHolder {

        public TextView mCaptionTextView;
        public TextView mAmountTextView;
        public TextView mColourTextView;
        public Button mAddBtn;

        public AddRedCardViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mCaptionTextView = itemView.findViewById(R.id.captionaddtextview);
            mAmountTextView = itemView.findViewById(R.id.amountaddtextview);
            mColourTextView = itemView.findViewById(R.id.colouraddtextview);
            mAddBtn = itemView.findViewById(R.id.addRemovedBtn);

            mAddBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddClick(position);
                        }
                    }
                }
            });
        }
    }
}

