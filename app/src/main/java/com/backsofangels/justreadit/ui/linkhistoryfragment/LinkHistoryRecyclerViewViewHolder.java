package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.backsofangels.justreadit.R;

public class LinkHistoryRecyclerViewViewHolder extends RecyclerView.ViewHolder {
    CardView cell;
    TextView linkUrl;
    TextView linkScanDate;

    public LinkHistoryRecyclerViewViewHolder (View cellView, final Context context) {
        super(cellView);
        cell = cellView.findViewById(R.id.recyclerview_cell);
        linkUrl = cellView.findViewById(R.id.recyclerview_cell_url);
        linkScanDate = cellView.findViewById(R.id.recyclerview_scandate);
        cell.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl.getText().toString()));
            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(browserIntent);
        });
    }
}
