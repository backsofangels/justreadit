package com.backsofangels.justreadit.persistence;

import com.backsofangels.justreadit.JustreaditApplication;
import com.backsofangels.justreadit.model.ScannedLink;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class ScannedLinkDao {
    private static ScannedLinkDao instance;
    private Realm r;

    private ScannedLinkDao() {}

    public static ScannedLinkDao getInstance() {
        if (instance == null) {
            instance = new ScannedLinkDao();
        }
        return instance;
    }

    public void setRealm(Realm activityRealm) {
        this.r = activityRealm;
    }

    public void saveLink(ScannedLink l) {
        try {
            r.beginTransaction();
            r.copyToRealm(l);
            r.commitTransaction();
        } catch (RealmException e) {
            System.out.println("Exception in saveLink, could not save the url");
            e.printStackTrace();
        }
    }

    public ArrayList<ScannedLink> retrieveLinks() {
        ArrayList<ScannedLink> queryResult = new ArrayList<>();
        System.out.println("Retrieving");
        try {
            queryResult.addAll(r.where(ScannedLink.class).findAll());
        } catch (RealmException e) {
            System.out.println("Exception in retrieveLinks, could not retrieve");
            e.printStackTrace();
        }
        return queryResult;
    }
}
