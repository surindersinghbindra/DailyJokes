package com.regrex.dailyJokes.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by surinder on 22-Jun-17.
 */

public class CategorySingle implements Parcelable {

    public static final Parcelable.Creator<CategorySingle> CREATOR = new Parcelable.Creator<CategorySingle>() {
        @Override
        public CategorySingle createFromParcel(Parcel source) {
            return new CategorySingle(source);
        }

        @Override
        public CategorySingle[] newArray(int size) {
            return new CategorySingle[size];
        }
    };
    public String categoryNameHindi, categoryEnglish;
    public int categoryId;

    public CategorySingle(int categoryId, String categoryNameHindi, String categoryEnglish) {
        this.categoryNameHindi = categoryNameHindi;
        this.categoryEnglish = categoryEnglish;
        this.categoryId = categoryId;
    }

    public CategorySingle() {
    }

    protected CategorySingle(Parcel in) {
        this.categoryNameHindi = in.readString();
        this.categoryId = in.readInt();
        this.categoryEnglish = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.categoryNameHindi);
        dest.writeInt(this.categoryId);
        dest.writeString(this.categoryEnglish);
    }
}
