package com.backsofangels.justreadit;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class JustreaditApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration CONF = new RealmConfiguration.Builder()
                .name("justreadit.realm")
                .build();
        Realm.setDefaultConfiguration(CONF);
    }
}
