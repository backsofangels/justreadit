package com.backsofangels.justreadit.ui.main;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.widget.Toast;

import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.ui.linkhistoryfragment.LinkHistoryFragment;
import com.backsofangels.justreadit.ui.qrcodefragment.QRCodeReaderFragment;

public class MainActivityFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    MainActivityFragmentPagerAdapter(Context context, FragmentManager fmanager) {
        super(fmanager);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new QRCodeReaderFragment();
            case 1:
                return new LinkHistoryFragment();
            default:
                Toast.makeText(mContext, "Ops, something went wrong", Toast.LENGTH_LONG).show();
        }

        return new Fragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return mContext.getString(R.string.scancode_tab_title);
            case 1:
                return mContext.getString(R.string.scanhistory_tab_title);
            default:
                return "Default";
        }
    }
}
