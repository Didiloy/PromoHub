package com.github.didiloy.promohub.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.github.didiloy.promohub.api.Deal;

@Entity(tableName = "deals")
public class DealEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;
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

    @Ignore
    public Deal toDeal(){
        Deal deal = new Deal();
        deal.internalName = internalName;
        deal.title = title;
        deal.external = external;
        deal.metacriticLink = metacriticLink;
        deal.dealID = dealID;
        deal.storeID = storeID;
        deal.gameID = gameID;
        deal.salePrice = salePrice;
        deal.normalPrice = normalPrice;
        deal.isOnSale = isOnSale;
        deal.savings = savings;
        deal.metacriticScore = metacriticScore;
        deal.steamRatingText = steamRatingText;
        deal.steamRatingPercent = steamRatingPercent;
        deal.steamRatingCount = steamRatingCount;
        deal.steamAppID = steamAppID;
        deal.releaseDate = releaseDate;
        deal.lastChange = lastChange;
        deal.dealRating = dealRating;
        deal.thumb = thumb;
        deal.cheapestDealID = cheapestDealID;
        deal.cheapest = cheapest;
        deal.isSaved = true;
        deal.promohub_database_id = uid;
        return deal;
    }

    @Ignore
    public static DealEntity fromDeal(Deal deal){
        DealEntity dealEntity = new DealEntity();
        dealEntity.internalName = deal.internalName;
        dealEntity.title = deal.title;
        dealEntity.external = deal.external;
        dealEntity.metacriticLink = deal.metacriticLink;
        dealEntity.dealID = deal.dealID;
        dealEntity.storeID = deal.storeID;
        dealEntity.gameID = deal.gameID;
        dealEntity.salePrice = deal.salePrice;
        dealEntity.normalPrice = deal.normalPrice;
        dealEntity.isOnSale = deal.isOnSale;
        dealEntity.savings = deal.savings;
        dealEntity.metacriticScore = deal.metacriticScore;
        dealEntity.steamRatingText = deal.steamRatingText;
        dealEntity.steamRatingPercent = deal.steamRatingPercent;
        dealEntity.steamRatingCount = deal.steamRatingCount;
        dealEntity.steamAppID = deal.steamAppID;
        dealEntity.releaseDate = deal.releaseDate;
        dealEntity.lastChange = deal.lastChange;
        dealEntity.dealRating = deal.dealRating;
        dealEntity.thumb = deal.thumb;
        dealEntity.cheapestDealID = deal.cheapestDealID;
        dealEntity.cheapest = deal.cheapest;
        if(deal.promohub_database_id != 0){
            dealEntity.uid = deal.promohub_database_id;
        }
        return dealEntity;
    }
}
