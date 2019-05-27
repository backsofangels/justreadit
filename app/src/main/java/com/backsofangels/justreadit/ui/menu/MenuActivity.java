package com.backsofangels.justreadit.ui.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.backsofangels.justreadit.R;

public class MenuActivity extends AppCompatActivity {
    private TextView licensesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_opensource_licenses);
        licensesTextView = findViewById(R.id.activity_menu_licenses_textview);
    }
}
