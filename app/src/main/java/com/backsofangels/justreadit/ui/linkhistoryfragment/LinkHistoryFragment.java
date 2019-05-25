package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.model.ScannedLink;
import com.backsofangels.justreadit.persistence.ScannedLinkDao;

import java.util.ArrayList;
import java.util.Collections;

public class LinkHistoryFragment extends Fragment {
    private static RecyclerView linkHistoryRecyclerView;
    private LinearLayoutManager linkHistoryLayoutManager;
    private static LinkHistoryRecyclerViewAdapter linkHistoryAdapter;
    private static ArrayList<ScannedLink> linkList;
    private ScannedLinkDao instance;
    private LinkHistorySwipeController controller;


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
        Collections.reverse(linkList);
        linkHistoryLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linkHistoryRecyclerView.setHasFixedSize(true);
        linkHistoryAdapter = new LinkHistoryRecyclerViewAdapter(linkList, getActivity().getApplicationContext());
        linkHistoryRecyclerView.setAdapter(linkHistoryAdapter);
        linkHistoryRecyclerView.setLayoutManager(linkHistoryLayoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(getActivity().getApplicationContext(), linkHistoryLayoutManager.getOrientation());
        linkHistoryRecyclerView.addItemDecoration(divider);
        controller = new LinkHistorySwipeController(new SwipeControllerActions() {
           @Override
           public void onRightClicked(int position) {
                linkHistoryAdapter.remove(position);
           }
        });
        ItemTouchHelper helper = new ItemTouchHelper(controller);
        helper.attachToRecyclerView(linkHistoryRecyclerView);

        linkHistoryRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent) {
                controller.onDraw(c);
            }
        });
    }

    public static void updateData() {
        linkList.clear();
        linkList.addAll(ScannedLinkDao.getInstance().retrieveLinks());
        linkHistoryAdapter.notifyDataSetChanged();
    }
}
