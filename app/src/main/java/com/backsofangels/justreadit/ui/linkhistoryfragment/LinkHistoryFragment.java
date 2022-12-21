package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
        Log.d(this.getClass().getCanonicalName(), " - Inflating fragment");
        View v = inflater.inflate(R.layout.linkhistory_layout, parent, false);
        linkHistoryRecyclerView = v.findViewById(R.id.linkhistory_recyclerview);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(this.getClass().getCanonicalName(), " - Activity created");
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
