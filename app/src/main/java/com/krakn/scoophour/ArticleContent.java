package com.krakn.scoophour;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class ArticleContent extends AppCompatActivity {

    private String title, url;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_content);

        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        url = intent.getStringExtra("URL");

        WebView webView = findViewById(R.id.webView);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.article_toolbar);
        TextView textView = findViewById(R.id.articleToolbarText);

        setSupportActionBar(toolbar);
        textView.setText(title);

        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(String.valueOf(url));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void fabClicked(View view) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareString = "Check out this new exciting story on Project-news app " + "\"" + title + "\"\t" + url;
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Article");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareString);
        startActivity(Intent.createChooser(shareIntent, "Share using"));
    }
}
