package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.model.ScannedLink;
import com.backsofangels.justreadit.persistence.ScannedLinkDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class LinkHistoryRecyclerViewAdapter extends RecyclerView.Adapter<LinkHistoryRecyclerViewViewHolder> {
    private ArrayList<ScannedLink> linkHistoryDataset;
    private Context context;

    LinkHistoryRecyclerViewAdapter (ArrayList<ScannedLink> dataset, Context context) {
        this.linkHistoryDataset = dataset;
        this.context = context;
    }

    @Override
    @NonNull
    public LinkHistoryRecyclerViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linkhistory_recyclerview_cell_layout, parent, false);
        return new LinkHistoryRecyclerViewViewHolder(v, context, new PopupMenuActions() {
            @Override
            public void onCopyPressed(int position) {
                ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("url to copy", linkHistoryDataset.get(position).getUrl());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(context, context.getResources().getString(R.string.copied_to_clipboard_toast), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDeletePressed(int position) {
                remove(position);
            }
        });
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

    public void update() {
        linkHistoryDataset.clear();
        linkHistoryDataset = ScannedLinkDao.getInstance().retrieveLinks();
        Collections.reverse(linkHistoryDataset);
        notifyDataSetChanged();
    }

    private void remove(int position) {
        ScannedLinkDao.getInstance().removeLinkFromRealm(linkHistoryDataset.get(position));
        linkHistoryDataset.remove(position);
        notifyItemRemoved(position);
    }

    //Updated to support every date format
    private String dateFormatter(Date date) {
        return DateFormat.getDateFormat(context).format(date);
    }
}
