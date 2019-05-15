package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.backsofangels.justreadit.R;

public class LinkHistoryRecyclerViewViewHolder extends RecyclerView.ViewHolder {
    protected CardView cell;
    protected TextView linkUrl;
    protected TextView linkScanDate;

    public LinkHistoryRecyclerViewViewHolder (View cellView) {
        super(cellView);
        cell = cellView.findViewById(R.id.recyclerview_cell);
        linkUrl = cellView.findViewById(R.id.recyclerview_cell_url);
        linkScanDate = cellView.findViewById(R.id.recyclerview_scandate);
    }
}
