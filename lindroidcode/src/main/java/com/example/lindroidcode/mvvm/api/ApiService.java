package com.example.lindroidcode.mvvm.api;

import com.example.lindroidcode.mvvm.vo.User;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    ApiService INSTANCE = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService.class);

    @GET("users/{login}")
    Call<User> getUser(@Path("login") String login);
}
