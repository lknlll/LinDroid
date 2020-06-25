package com.example.lindroidcode.mvvm.vo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final String message;

    @Nullable
    public final T data;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    //所有泛型方法声明都有一个类型参数声明部分（由尖括号分隔），该类型参数声明部分在方法返回类型之前（<T>）。
    public static <T> Resource<T> success(@Nullable T data){
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data){
        return new Resource<>(Status.ERROR,data,msg);
    }
    public static <T> Resource<T> loading(@Nullable T data){
        return new Resource<>(Status.LOADING,data,null);
    }
}
