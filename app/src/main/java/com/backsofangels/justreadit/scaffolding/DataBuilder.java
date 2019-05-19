package com.backsofangels.justreadit.scaffolding;

import com.backsofangels.justreadit.model.ScannedLink;
import com.backsofangels.justreadit.persistence.ScannedLinkDao;

import java.util.ArrayList;
import java.util.Date;


//TODO: remove
public class DataBuilder {
    private static DataBuilder instance;
    private static ScannedLinkDao dao;

    private DataBuilder() {}

    public static DataBuilder getInstance() {
        if (instance == null) {
            instance = new DataBuilder();
        }
        return instance;
    }
}
