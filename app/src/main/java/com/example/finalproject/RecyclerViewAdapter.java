package com.example.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<MonsterItem> mMonsterList;
    private Context mContext;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mMonsterName;


        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.monsterimg);
            mMonsterName = itemView.findViewById(R.id.nameText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public RecyclerViewAdapter(Context context, ArrayList<MonsterItem> monsterItemsList) {
        mMonsterList = monsterItemsList;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.character_list, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MonsterItem currentItem = mMonsterList.get(i);

        String monsterName = currentItem.getNameText();

        String imageName = currentItem.getMonsterImageResouce();
        int imageResID = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        viewHolder.mMonsterName.setText(monsterName);
        viewHolder.mImageView.setImageResource(imageResID);

    }

    @Override
    public int getItemCount() {
        return mMonsterList.size();
    }
}