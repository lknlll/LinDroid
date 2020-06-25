package com.example.lindroidcode.mvvm.vo;

import com.google.gson.annotations.SerializedName;

public class User {
    public String name;
    @SerializedName("avatar_url")
    public String avatarUrl;
    @SerializedName("updated_at")
    public String lastUpdate;
    @SerializedName("public_repos")
    public int repoNumber;
}
