package com.github.didiloy.promohub.api;

import android.os.Parcel;
import android.os.Parcelable;

public class Deal implements Parcelable {

    public String internalName;
    public String title;
    public String external;
    public String metacriticLink;
    public String dealID;
    public String storeID;
    public String gameID;
    public String salePrice;
    public String normalPrice;
    public boolean isOnSale;
    public double savings;
    public int metacriticScore;
    public String steamRatingText;
    public int steamRatingPercent;
    public int steamRatingCount;
    public String steamAppID;
    public long releaseDate;
    public long lastChange;
    public double dealRating;
    public String thumb;
    public String cheapestDealID;
    public String cheapest;

    protected Deal(Parcel in) {
        internalName = in.readString();
        title = in.readString();
        external = in.readString();
        metacriticLink = in.readString();
        dealID = in.readString();
        storeID = in.readString();
        gameID = in.readString();
        salePrice = in.readString();
        normalPrice = in.readString();
        isOnSale = in.readByte() != 0; // Read boolean as byte
        savings = in.readDouble();
        metacriticScore = in.readInt();
        steamRatingText = in.readString();
        steamRatingPercent = in.readInt();
        steamRatingCount = in.readInt();
        steamAppID = in.readString();
        releaseDate = in.readLong();
        lastChange = in.readLong();
        dealRating = in.readDouble();
        thumb = in.readString();
        cheapestDealID = in.readString();
        cheapest = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(internalName);
        dest.writeString(title);
        dest.writeString(external);
        dest.writeString(metacriticLink);
        dest.writeString(dealID);
        dest.writeString(storeID);
        dest.writeString(gameID);
        dest.writeString(salePrice);
        dest.writeString(normalPrice);
        dest.writeByte((byte) (isOnSale ? 1 : 0));
        dest.writeDouble(savings);
        dest.writeInt(metacriticScore);
        dest.writeString(steamRatingText);
        dest.writeInt(steamRatingPercent);
        dest.writeInt(steamRatingCount);
        dest.writeString(steamAppID);
        dest.writeLong(releaseDate);
        dest.writeLong(lastChange);
        dest.writeDouble(dealRating);
        dest.writeString(thumb);
        dest.writeString(cheapestDealID);
        dest.writeString(cheapest);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Deal> CREATOR = new Creator<Deal>() {
        @Override
        public Deal createFromParcel(Parcel in) {
            return new Deal(in);
        }

        @Override
        public Deal[] newArray(int size) {
            return new Deal[size];
        }
    };
}
