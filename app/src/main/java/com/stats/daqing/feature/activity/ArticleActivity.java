package com.stats.daqing.feature.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;

import utils.AssetsUtil;

public class ArticleActivity extends BaseActivity {

    private TintToolbar mToolBar;
    private ProgressBar bar;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        revMsg();
        initVariable();
        initView();
        initData();
    }



    private void revMsg() {

    }

    private void initVariable() {

    }

    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        bar = (ProgressBar) findViewById(R.id.myProgressBar);
        webView = (WebView) findViewById(R.id.webview);


        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(null);
        // 设置返回按钮
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initData() {
        String data = AssetsUtil.readFile("html3.txt");
        webView.loadDataWithBaseURL(null,data,"text/html", "utf-8", null);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
