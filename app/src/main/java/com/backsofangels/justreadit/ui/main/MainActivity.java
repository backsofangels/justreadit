package com.backsofangels.justreadit.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.persistence.ScannedLinkDao;
import com.backsofangels.justreadit.ui.menu.PrivacyLicensesActivity;


import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Realm r;
    private static final int requestCode = 100;

    //View components
    private TabLayout tabLayout;
    private ViewPager pager;
    private MainActivityFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        askForCameraPermission(this, this, requestCode);
        setupViewComponents();

        r = Realm.getDefaultInstance();
        ScannedLinkDao.getInstance().setRealm(r);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, PrivacyLicensesActivity.class);
        intent.putExtra("menuItem", item.getItemId());
        startActivity(intent);
        return true;
    }

    private void setupViewComponents() {
        tabLayout = findViewById(R.id.main_tablayout);
        pager = findViewById(R.id.main_viewpager);
        adapter = new MainActivityFragmentPagerAdapter(this, getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    //Cares about closing the realm when the activity is terminated
    @Override
    protected void onDestroy() {
        super.onDestroy();
        r.close();
    }

    //Moved the permission logic in separate function
    private void askForCameraPermission(Context context, Activity activity, int requestCode) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.CAMERA}, requestCode);
        }
    }
}