package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.model.ScannedLink;
import com.backsofangels.justreadit.persistence.ScannedLinkDao;

import java.util.ArrayList;
import java.util.Collections;

public class LinkHistoryFragment extends Fragment {
    private RecyclerView linkHistoryRecyclerView;
    private LinkHistoryRecyclerViewAdapter linkHistoryAdapter;
    private static ArrayList<ScannedLink> linkList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.linkhistory_layout, parent, false);
        linkHistoryRecyclerView = v.findViewById(R.id.linkhistory_recyclerview);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupView();
        ScannedLinkDao.getInstance().setAdapter(linkHistoryAdapter);
    }

    //Check if this happens on UI thread or not (should this be done asynchrously?)
    private void fillDataset() {
        linkList = ScannedLinkDao.getInstance().retrieveLinks();
        Collections.reverse(linkList);
    }

    private void setupView() {
        fillDataset();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linkHistoryRecyclerView.setHasFixedSize(true);
        linkHistoryAdapter = new LinkHistoryRecyclerViewAdapter(linkList, getActivity().getApplicationContext());
        linkHistoryRecyclerView.setAdapter(linkHistoryAdapter);
        linkHistoryRecyclerView.setLayoutManager(manager);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity().getApplicationContext(), manager.getOrientation());
        linkHistoryRecyclerView.addItemDecoration(divider);
    }
}
