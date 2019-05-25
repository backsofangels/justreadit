package com.backsofangels.justreadit;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraMailSender;
import org.acra.annotation.AcraNotification;

import io.realm.Realm;
import io.realm.RealmConfiguration;

@AcraCore(buildConfigClass = BuildConfig.class)
@AcraMailSender(mailTo = "backsofangels@gmail.com", reportAsFile = true, reportFileName = "JustReadIt-ACRA-report.stacktrace", resSubject = R.string.acrareport_mailsubject)
@AcraNotification(resChannelName = R.string.acrareport_notificationchannel_name, resTitle = R.string.acrareport_notification_title, resText = R.string.acrareport_notification_text)
public class JustreaditApplication extends Application {
    //Realm DB setup
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        RealmConfiguration CONF = new RealmConfiguration.Builder()
                .name("justreadit.realm")
                .build();
        Realm.setDefaultConfiguration(CONF);
        createNotificationChannel();
    }

    //ACRA (crash report service) configuration
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ACRA.init(this);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.acrareport_notificationchannel_name);
            String description = getString(R.string.acrareport_notificationchannel_description);
            String channelId = getString(R.string.acrareport_notificationchannel_id);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
