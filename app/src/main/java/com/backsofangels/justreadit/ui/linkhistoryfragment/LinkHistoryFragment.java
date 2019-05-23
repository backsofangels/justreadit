package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.model.ScannedLink;
import com.backsofangels.justreadit.persistence.ScannedLinkDao;
import com.backsofangels.justreadit.scaffolding.DataBuilder;

import java.util.ArrayList;

public class LinkHistoryFragment extends Fragment {
    private static RecyclerView linkHistoryRecyclerView;
    private RecyclerView.LayoutManager linkHistoryLayoutManager;
    private static LinkHistoryRecyclerViewAdapter linkHistoryAdapter;
    private static ArrayList<ScannedLink> linkList;
    private ScannedLinkDao instance;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.linkhistory_layout, parent, false);
        linkHistoryRecyclerView = v.findViewById(R.id.linkhistory_recyclerview);
        return v;
    }

    //TODO: Aggiustare questo metodo che fa schifo al cazzo
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        instance = ScannedLinkDao.getInstance();
        linkList = instance.retrieveLinks();
        linkHistoryLayoutManager = new LinearLayoutManager(getContext());
        linkHistoryRecyclerView.setHasFixedSize(true);
        linkHistoryAdapter = new LinkHistoryRecyclerViewAdapter(linkList, getActivity().getApplicationContext());
        linkHistoryRecyclerView.setAdapter(linkHistoryAdapter);
        linkHistoryRecyclerView.setLayoutManager(linkHistoryLayoutManager);
    }

    public static void updateData() {
        linkList.clear();
        linkList.addAll(ScannedLinkDao.getInstance().retrieveLinks());
        linkHistoryAdapter.notifyDataSetChanged();
    }
}
