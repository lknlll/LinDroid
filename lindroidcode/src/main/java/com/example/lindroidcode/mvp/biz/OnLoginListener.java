package com.example.lindroidcode.mvp.biz;
import com.example.lindroidcode.mvp.beans.User;

/**
 *
 */
public interface OnLoginListener{
    void loginSuccess(User user);

    void loginFailed();
}

