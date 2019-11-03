package com.example.ivysaur.utils;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmUtils {

    public static Realm getRealmInstance(){
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        return Realm.getInstance(realmConfig);
    }
}
