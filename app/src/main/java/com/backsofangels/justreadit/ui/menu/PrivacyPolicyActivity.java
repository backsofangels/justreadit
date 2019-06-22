package com.backsofangels.justreadit.ui.menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.backsofangels.justreadit.R;
import com.mukesh.MarkdownView;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        MarkdownView markdownView = findViewById(R.id.privacy_policy_markdown);
        markdownView.loadMarkdownFromAssets("privacy_policy.md");
    }
}
