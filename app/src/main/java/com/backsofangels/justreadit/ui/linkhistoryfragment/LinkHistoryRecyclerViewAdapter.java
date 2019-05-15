package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.model.ScannedLink;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LinkHistoryRecyclerViewAdapter extends RecyclerView.Adapter<LinkHistoryRecyclerViewViewHolder> {
    private ArrayList<ScannedLink> linkHistoryDataset;
    private Context context;

    public LinkHistoryRecyclerViewAdapter (ArrayList<ScannedLink> dataset, Context context) {
        this.linkHistoryDataset = dataset;
        this.context = context;
    }

    @Override
    @NonNull
    public LinkHistoryRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linkhistory_recyclerview_cell_layout, parent, false);
        return new LinkHistoryRecyclerViewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkHistoryRecyclerViewViewHolder holder, int position) {
        holder.linkUrl.setText(linkHistoryDataset.get(position).getUrl());
        holder.linkScanDate.setText(dateFormatter(linkHistoryDataset.get(position).getScanDate()));
    }

    @Override
    public int getItemCount() {
        return linkHistoryDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView r) {
        super.onAttachedToRecyclerView(r);
    }

    public void insert(int position, ScannedLink data) {
        linkHistoryDataset.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(ScannedLink data) {
        int pos = linkHistoryDataset.indexOf(data);
        linkHistoryDataset.remove(data);
        notifyItemRemoved(pos);
    }

    private String dateFormatter(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return DateFormat.format("dd/MM/yyyy", cal).toString();
    }
}
