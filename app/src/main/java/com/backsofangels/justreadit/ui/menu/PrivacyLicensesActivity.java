package com.backsofangels.justreadit.ui.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.backsofangels.justreadit.R;
import com.mukesh.MarkdownView;

public class PrivacyLicensesActivity extends AppCompatActivity {
    private int itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_licenses);
        MarkdownView markdownView = findViewById(R.id.privacy_policy_markdown);

        try {
            itemId = getIntent().getExtras().getInt("menuItem");
        } catch (NullPointerException e) {
            itemId = R.id.open_source_licenses;
            Toast.makeText(this, "Ops, an error occurred. Report the issue on GitHub!", Toast.LENGTH_LONG).show();
        }

        String licensesFilename = getString(R.string.licenses_filename);
        switch (itemId) {
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
