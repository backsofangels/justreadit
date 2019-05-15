package com.backsofangels.justreadit.model;

import java.util.Date;

public class ScannedLink {
    private String Url;
    private Date scanDate;

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
