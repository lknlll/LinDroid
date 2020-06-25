package com.example.lindroidcode.mvvm.api;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class ApiResponse<T> {
    public final int code;
    public final T body;
    public final String errorMessage;

    public ApiResponse(Throwable error){
        code = 500;
        body = null;
        errorMessage = error.getMessage();

    }

    public ApiResponse(Response<T> response){
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        }else {
            String message = null;
            ResponseBody errorBody = response.errorBody();
            if (errorBody != null) {
                try {
                    message = errorBody.string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }
}
