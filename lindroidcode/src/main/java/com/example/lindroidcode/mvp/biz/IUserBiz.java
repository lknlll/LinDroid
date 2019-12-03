package com.example.lindroidcode.mvp.biz;

/**
 *
 */

public interface IUserBiz {
    public void login(String username, String password, OnLoginListener loginListener);
}

