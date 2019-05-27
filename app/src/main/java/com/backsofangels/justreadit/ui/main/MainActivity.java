package com.backsofangels.justreadit.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.backsofangels.justreadit.R;
import com.backsofangels.justreadit.persistence.ScannedLinkDao;
import com.backsofangels.justreadit.ui.menu.MenuActivity;


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
        if (item.getItemId() == R.id.open_source_licenses) {
            System.out.println("starto la nuova activity");
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            return true;
        } else return false;
    }

    private void setupViewComponents() {
        tabLayout = findViewById(R.id.main_tablayout);
        pager = findViewById(R.id.main_viewpager);
        adapter = new MainActivityFragmentPagerAdapter(this, getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
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