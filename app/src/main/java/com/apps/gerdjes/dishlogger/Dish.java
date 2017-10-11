package com.apps.gerdjes.dishlogger;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by vdabcursist on 11/10/2017.
 */

public class Dish {

    @DatabaseField(generatedId = true)
    private int accountId;

    @DatabaseField
    private String name;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

