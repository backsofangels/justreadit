package com.backsofangels.justreadit.ui.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.backsofangels.justreadit.R;
import com.mukesh.MarkdownView;

public class PrivacyLicensesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_licenses);
        MarkdownView markdownView = findViewById(R.id.privacy_policy_markdown);

        Integer item = getIntent().getExtras().getInt("menuItem");
        if (item == R.id.menu_item_privacy_policy) {
            System.out.println("privacy");
        } else if (item == R.id.open_source_licenses) {
            System.out.println("licenze");
        }
        String licensesFilename = getString(R.string.licenses_filename);
        System.out.println(licensesFilename);

        switch (item) {
            case R.id.open_source_licenses:
                markdownView.loadMarkdownFromAssets(licensesFilename);
                break;
            case R.id.menu_item_privacy_policy:
                markdownView.loadMarkdownFromAssets("privacy_policy.md");
                break;
                default:
                    markdownView.loadMarkdownFromAssets("licenses-en.md");
                    break;
        }
    }
}
