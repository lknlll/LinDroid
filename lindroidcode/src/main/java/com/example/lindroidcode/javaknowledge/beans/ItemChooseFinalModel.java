package com.example.lindroidcode.javaknowledge.beans;

import android.text.TextUtils;
import android.util.Log;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ItemChooseFinalModel implements Serializable {
    @SerializedName("globalId")
    private String globalId;
    @SerializedName("globalIdStr")
    private String globalIdStr;
    @SerializedName("name")
    private String name;
    @SerializedName("imageUrl")
    private String imageUrl;
    @SerializedName("recentSales")
    private int recentSales;
    @SerializedName("enabled")
    private boolean enabled;
    @SerializedName("enabledPrompt")
    private String enabledPrompt;
    @SerializedName("skuData")
    private ItemCardSku sku = new ItemCardSku();
    @SerializedName("isSelect")
    private boolean isSelect;
    @SerializedName("isClose")
    private boolean isClose = true;
    @SerializedName("groupGlobalId")
    private String groupGlobalId;
    @SerializedName("groupGlobalIdStr")
    private String groupGlobalIdStr;
    @SerializedName("isMoreSpec")
    private boolean isMoreSpec;
    private int quantity = 0;
    private String unit = "份";

    public ItemChooseFinalModel() {
    }

    public String getGroupGlobalIdStr() {
        return this.groupGlobalIdStr;
    }

    public void setGroupGlobalIdStr(String groupGlobalIdStr) {
        this.groupGlobalIdStr = groupGlobalIdStr;
    }

    public String getGlobalIdStr() {
        return this.globalIdStr;
    }

    public void setGlobalIdStr(String globalIdStr) {
        this.globalIdStr = globalIdStr;
    }

    public boolean isMoreSpec() {
        return this.isMoreSpec;
    }

    public void setMoreSpec(boolean moreSpec) {
        this.isMoreSpec = moreSpec;
    }

    public boolean equals(ItemChooseFinalModel obj) {
        return obj != null && obj.sku != null && this.sku != null && !TextUtils.isEmpty(obj.sku.getGlobalId()) && obj.sku.getGlobalId().equals(this.sku.getGlobalId());
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

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getRecentSales() {
        return this.recentSales;
    }

    public void setRecentSales(int recentSales) {
        this.recentSales = recentSales;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String isEnabledPrompt() {
        return this.enabledPrompt;
    }

    public void setEnabledPrompt(String enabledPrompt) {
        this.enabledPrompt = enabledPrompt;
    }

    public ItemCardSku getSku() {
        return this.sku;
    }

    public void setSku(ItemCardSku sku) {
        this.sku = sku;

        try {
            if (sku != null && !TextUtils.isEmpty(sku.getGlobalId()) && Long.parseLong(sku.getGlobalId()) > 0L) {
                sku.setGlobalIdStr(String.valueOf(sku.getGlobalId()));
            }
        } catch (Exception var3) {
            sku.setGlobalIdStr("");
        }

    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean select) {
        this.isSelect = select;
    }

    public boolean isClose() {
        return this.isClose;
    }

    public void setClose(boolean close) {
        this.isClose = close;
    }

    public String getGroupGlobalId() {
        return this.groupGlobalId;
    }

    public void setGroupGlobalId(String groupGlobalId) {
        this.groupGlobalId = groupGlobalId;
    }

    public String getSelectSkuId() {
        return this.sku != null ? this.sku.getGlobalId() : "-1";
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void updateGlobalIdStr() {
        long skuId;
        if (!TextUtils.isEmpty(this.globalIdStr)) {
            skuId = 0L;

            try {
                skuId = Long.parseLong(this.globalIdStr);
            } catch (Exception var5) {
            }

            if (skuId > 0L) {
                this.globalId = String.valueOf(skuId);
            }

            Log.d("updateGlobalIdStr: ", "globalId: " + this.globalId + " id: " + skuId);
        }

        if (this.sku != null && !TextUtils.isEmpty(this.sku.getGlobalIdStr())) {
            skuId = 0L;

            try {
                skuId = Long.parseLong(this.sku.getGlobalIdStr());
            } catch (Exception var4) {
            }

            if (skuId > 0L) {
                this.sku.setGlobalId(String.valueOf(skuId));
            }

            Log.d("sku updateGlobalIdStr: ", "globalId: " + this.sku.getGlobalId() + " skuId: " + skuId);
        }

    }
}
