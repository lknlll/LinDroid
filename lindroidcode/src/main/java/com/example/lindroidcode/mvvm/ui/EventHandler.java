package com.example.lindroidcode.mvvm.ui;

public class EventHandler {
    private GithubProfileActivity mActivity;

    EventHandler(GithubProfileActivity githubProfileActivity){
        this.mActivity = githubProfileActivity;
    }

    /*
     * 这个方法由xml中的app:onInputFinish="@{(text)->eventHandler.onTextSubmit(text)}"调用。
     */
    public void onTextSubmit(String text){
        mActivity.onSearchUser(text);
    }
}
