package com.example.lindroidcode.mvvm.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.lindroidcode.mvvm.repo.UserRepo;
import com.example.lindroidcode.mvvm.vo.Resource;
import com.example.lindroidcode.mvvm.vo.User;

public class GithubProfileViewModel extends ViewModel {
    private final UserRepo userRepo = UserRepo.getInstance();
    private final MutableLiveData<String> userNameLiveData = new MutableLiveData<>();
    private final LiveData<Resource<User>> userEntityLiveData;

    public GithubProfileViewModel(){

        userEntityLiveData = Transformations.switchMap(userNameLiveData,input -> {

            if (input != null) {
                return userRepo.getUser(input);
            }else {
                return new MutableLiveData<>();
            }
        });
    }

    public LiveData<Resource<User>> getUser() {
        return userEntityLiveData;
    }

    public void setUserName(String userName){
        userNameLiveData.postValue(userName);
    }
}
