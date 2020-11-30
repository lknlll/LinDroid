package com.example.lindroidcode.greendaoatoz.beans;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

public class LongConverter implements PropertyConverter<List<Long>,String> {

    @Override
    public List<Long> convertToEntityProperty(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }
        // 先得获得这个，然后再typeToken.getType()，否则会异常
        TypeToken<List<Long>> typeToken = new TypeToken<List<Long>>(){};
        return new Gson().fromJson(databaseValue, typeToken.getType());
    }

    @Override
    public String convertToDatabaseValue(List<Long> entityProperty) {
        if (entityProperty == null || entityProperty.size() == 0) {
            return "null";
        }else {
            return new Gson().toJson(entityProperty);
        }
    }
}
