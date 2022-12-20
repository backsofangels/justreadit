package com.backsofangels.justreadit.ui.linkhistoryfragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.backsofangels.justreadit.R;

class LinkHistoryRecyclerViewViewHolder extends RecyclerView.ViewHolder {
    private CardView cell;
    private ImageButton popupMenuButton;
    TextView linkUrl;
    TextView linkScanDate;
    private PopupMenuActions actions;

    LinkHistoryRecyclerViewViewHolder (View cellView, final Context context, PopupMenuActions actions) {
        super(cellView);
        cell = cellView.findViewById(R.id.recyclerview_cell);
        linkUrl = cellView.findViewById(R.id.recyclerview_cell_url);
        linkScanDate = cellView.findViewById(R.id.recyclerview_scandate);
        popupMenuButton = cellView.findViewById(R.id.recyclerview_cell_popup_menu_button);
        cell.setOnClickListener(v -> {
            //Open in browser intent
            if (URLUtil.isValidUrl(linkUrl.getText().toString())) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl.getText().toString()));
                browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);
            }
        });

        this.actions = actions;

        popupMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(popupMenuButton.getContext(), cellView);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.recyclerview_popup_share:
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.putExtra(Intent.EXTRA_TEXT, prepareLinkToShare(linkUrl.getText().toString()));
                                intent.setType("text/plain");
                                context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.recyclerview_cell_send_to)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                                return true;
                            case R.id.recyclerview_popup_copy:
                                actions.onCopyPressed(getAdapterPosition());
                                return true;
                            case R.id.recyclerview_popup_delete:
                                actions.onDeletePressed(getAdapterPosition());
                                return true;
                                default:
                                    return false;

                        }
                    }
                });

                menu.inflate(R.menu.recyclerview_popup_menu);
                menu.setGravity(Gravity.END);
                menu.show();
            }
        });


    }

    private String prepareLinkToShare(String url) {
        return url + " - via Just Read It! https://bit.ly/justreaditapp";
    }
}
