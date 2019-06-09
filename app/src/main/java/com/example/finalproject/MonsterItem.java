package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class MonsterItem implements Parcelable {

    private String imageName;
    private String mNameText;
    private String debitCardNumber;
    private String creditCardNumber;

    public MonsterItem(String imageResourse, String nameText, String debitCardNumber, String creditCardNumber) {
        imageName = imageResourse;
        mNameText = nameText;
        this.debitCardNumber = debitCardNumber;
        this.creditCardNumber = creditCardNumber;

    }

    protected MonsterItem(Parcel in) {
        imageName = in.readString();
        mNameText = in.readString();
        debitCardNumber = in.readString();
        creditCardNumber = in.readString();
    }

    public static final Creator<MonsterItem> CREATOR = new Creator<MonsterItem>() {
        @Override
        public MonsterItem createFromParcel(Parcel in) {
            return new MonsterItem(in);
        }

        @Override
        public MonsterItem[] newArray(int size) {
            return new MonsterItem[size];
        }
    };

    public String getMonsterImageResouce() {
        return imageName;
    }

    public String getNameText() {
        return mNameText;
    }

    public String getDebitCardNumber() { return debitCardNumber; }

    public String getCreditCardNumber() {return creditCardNumber; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageName);
        dest.writeString(mNameText);
        dest.writeString(debitCardNumber);
        dest.writeString(creditCardNumber);
    }
}
