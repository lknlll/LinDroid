package com.example.lindroidcode.mvvm.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.lindroidcode.R;
import com.example.lindroidcode.databinding.ActivityGithubProfileBinding;
import com.example.lindroidcode.mvvm.vo.Resource;
import com.example.lindroidcode.mvvm.vo.User;

public class GithubProfileActivity extends AppCompatActivity {

    //xml中，根节点变为layout，并且下面<data><variable>以后编译会自动生成Binding类
    private ActivityGithubProfileBinding mGithubProfileBinding;

    private GithubProfileViewModel mGithubProfileViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView替换
        mGithubProfileBinding = DataBindingUtil.setContentView(this,R.layout.activity_github_profile);

        //不能new ViewModel，而是要使用创建工厂，此处getter实际上是反射生成ViewModel对象，创建无视图Fragment持有ViewModel对象，进而感知Activity生命周期，Activity销毁时ViewModel onClear()方法触发销毁ViewModel
        mGithubProfileViewModel = ViewModelProviders.of(this).get(GithubProfileViewModel.class);
        mGithubProfileBinding.setEventHandler(new EventHandler(this));
        LiveData<Resource<User>> userLiveData = mGithubProfileViewModel.getUser();
        //观察数据User的变化，不需要removeObserve，LiveData同样可以感知Activity生命周期并自动销毁
        userLiveData.observe(this,userResource -> {
            //setter 根据xml <variable>标签自动生成
            mGithubProfileBinding.setLoadStatus(userResource == null? null: userResource.status);
            mGithubProfileBinding.setUser(userResource == null? null: userResource.data);
            mGithubProfileBinding.setResource(userResource);
        });
    }

    void onSearchUser(String text){
        mGithubProfileViewModel.setUserName(text);
    }
}