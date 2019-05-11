package com.backsofangels.justreadit.scaffolding;

import com.backsofangels.justreadit.model.ScannedLink;

import java.util.ArrayList;
import java.util.Date;

public class DataBuilder {
    private static DataBuilder instance;

    private DataBuilder() {};

    public static DataBuilder getInstance() {
        if (instance == null) {
            instance = new DataBuilder();
        }
        return instance;
    }

    //Returns a new scanned link with ScanDate set to the creation moment with a fixed URL
    public ScannedLink createLink() {
        return new ScannedLink("https://github.com/backsofangels", new Date());
    }

    //Returns a variable number of links defined with the param arrayLength
    public ArrayList<ScannedLink> createLinkArrayList(int arrayLength) {
        ArrayList<ScannedLink> linkList = new ArrayList<>();

        for (int i = 0; i < arrayLength; i++) {
            linkList.add(createLink());
        }

        return linkList;
    }
}
