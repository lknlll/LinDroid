package com.example.lindroidcode.javaknowledge.beans;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class SkuRelationItem implements Serializable {
    @SerializedName("itemName")
    private String itemName;
    @SerializedName("groupId")
    private String groupId;
    @SerializedName("itemId")
    private String itemId;
    @SerializedName("itemUrl")
    private String itemUrl;

    public SkuRelationItem() {
    }

    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemUrl() {
        return this.itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}