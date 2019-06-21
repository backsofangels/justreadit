package com.backsofangels.justreadit.persistence;

import com.backsofangels.justreadit.model.ScannedLink;
import com.backsofangels.justreadit.ui.linkhistoryfragment.LinkHistoryRecyclerViewAdapter;

import java.util.ArrayList;

import javax.annotation.Nonnull;

import io.realm.Realm;
import io.realm.exceptions.RealmException;

public class ScannedLinkDao {
    private static ScannedLinkDao instance;
    private LinkHistoryRecyclerViewAdapter adapter; //Need it to call the update
    private Realm r;

    private ScannedLinkDao() {}

    public static ScannedLinkDao getInstance() {
        if (instance == null) {
            instance = new ScannedLinkDao();
        }
        return instance;
    }

    public void setAdapter(LinkHistoryRecyclerViewAdapter linkAdapter) {
        this.adapter = linkAdapter;
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
        } finally {
            adapter.update();
        }
    }

    public void removeLinkFromRealm(ScannedLink link) {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@Nonnull Realm realm) {
                link.deleteFromRealm();
            }
        });
    }

    public ArrayList<ScannedLink> retrieveLinks() {
        ArrayList<ScannedLink> queryResult = new ArrayList<>();
        try {
            queryResult.addAll(r.where(ScannedLink.class).findAll());
        } catch (RealmException e) {
            System.out.println("Exception in retrieveLinks, could not retrieve");
            e.printStackTrace();
        }
        return queryResult;
    }
}
