package com.backsofangels.justreadit.model;

import java.util.Date;

import io.realm.RealmObject;

public class ScannedLink extends RealmObject {
    private String Url;
    private Date scanDate;

    public ScannedLink() {}

    public ScannedLink(String Url, Date scanDate) {
        this.Url = Url;
        this.scanDate = scanDate;
    }

    public void setURL (String newUrl) {
        this.Url = newUrl;
    }

    public void setScanDate(Date scanDate) {
        this.scanDate = scanDate;
    }

    public String getUrl() {
        return this.Url;
    }

    public Date getScanDate() {
        return this.scanDate;
    }
}
