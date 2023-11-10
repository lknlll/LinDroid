package com.example.lindroidcode.javaknowledge.beans;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemCardSku implements Serializable {
    @SerializedName("globalId")
    private String globalId;
    @SerializedName("recentSales")
    private int recentSales;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private double price;
    @SerializedName("activityPrice")
    private double activityPrice;
    @SerializedName("packageFee")
    private double packageFee;
    @SerializedName("stock")
    private int stock;
    @SerializedName("isSelect")
    private boolean isSelect;
    @SerializedName("relationItem")
    private SkuRelationItem relationItem;
    @SerializedName("globalIdStr")
    private String globalIdStr;

    public ItemCardSku() {
    }

    public String getGlobalIdStr() {
        return this.globalIdStr;
    }

    public void setGlobalIdStr(String globalIdStr) {
        this.globalIdStr = globalIdStr;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public int getRecentSales() {
        return this.recentSales;
    }

    public void setRecentSales(int recentSales) {
        this.recentSales = recentSales;
    }

    public String getGlobalId() {
        return this.globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getActivityPrice() {
        return this.activityPrice;
    }

    public void setActivityPrice(double activityPrice) {
        this.activityPrice = activityPrice;
    }

    public double getPackageFee() {
        return this.packageFee;
    }

    public void setPackageFee(double packageFee) {
        this.packageFee = packageFee;
    }

    public int getStock() {
        return this.stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public SkuRelationItem getRelationItem() {
        return this.relationItem;
    }

    public void setRelationItem(SkuRelationItem relationItem) {
        this.relationItem = relationItem;
    }
}
