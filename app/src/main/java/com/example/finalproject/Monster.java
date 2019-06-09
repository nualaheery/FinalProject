package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class Monster implements Parcelable {

    private int mMonsterImageResouce;
    private String mNameText;

    public Monster(int imageResourse, String nameText) {
        mMonsterImageResouce = imageResourse;
        mNameText = nameText;

    }

    protected Monster(Parcel in) {
        mMonsterImageResouce = in.readInt();
        mNameText = in.readString();
    }

    public static final Creator<Monster> CREATOR = new Creator<Monster>() {
        @Override
        public Monster createFromParcel(Parcel in) {
            return new Monster(in);
        }

        @Override
        public Monster[] newArray(int size) {
            return new Monster[size];
        }
    };

    public int getMonsterImageResouce() {
        return mMonsterImageResouce;
    }

    public String getNameText() {
        return mNameText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMonsterImageResouce);
        dest.writeString(mNameText);
    }
}
