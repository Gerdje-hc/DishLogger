package com.apps.gerdjes.dishlogger;

import com.j256.ormlite.field.DatabaseField;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by vdabcursist on 11/10/2017.
 */

public class DishType {



    @DatabaseField(generatedId = true)
    private int accountId;

    @DatabaseField
    private String dishTypeName;

    public DishType() {
    }

    public DishType(String dishTypeName) {
        this.dishTypeName = dishTypeName;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getDishTypeName() {
        return dishTypeName;
    }

    public void setDishTypeName(String dishTypeName) {
        this.dishTypeName = dishTypeName;
    }
}
