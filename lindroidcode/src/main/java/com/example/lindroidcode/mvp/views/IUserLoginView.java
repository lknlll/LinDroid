package com.example.lindroidcode.mvp.views;

import com.example.lindroidcode.mvp.beans.User;

public interface IUserLoginView
{
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    /**
     * 登录成功跳转Activity
     * @param user
     */
    void toMainActivity(User user);

    /**
     * 登录失败
     */
    void showFailedError();

}