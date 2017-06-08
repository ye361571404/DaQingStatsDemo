package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.litesuits.common.utils.NumberUtil;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.ArticlesBean;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.utils.TimeUtil;

import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;

public class ArticlesActivity extends BaseActivity implements View.OnClickListener {

    private TintToolbar mToolBar;
    private ProgressBar bar;
    private WebView webView;
    private ArrayList<ArticlesBean.ArticlesListBean> articlesList;
    private TextView tvPrevious;
    private TextView tvNext;
    private int currentPosition;
    private LinearLayout llPrevisou;
    private TextView tvPreviousContent;
    private LinearLayout llNext;
    private TextView tvNextContent;
    /** 文章标题 **/
    private TextView tvTitle;
    /** 文章发布时间 **/
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        revMsg();
        initVariable();
        initView();
        setListener();
        initData();
    }

    private void setListener() {
        llPrevisou.setOnClickListener(this);
        llNext.setOnClickListener(this);
    }


    private void revMsg() {
        Intent intent = getIntent();
        articlesList = intent.getParcelableArrayListExtra("articlesList");

    }

    private void initVariable() {

    }

    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        bar = (ProgressBar) findViewById(R.id.myProgressBar);


        tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTime = (TextView)findViewById(R.id.tv_time);

        llPrevisou = (LinearLayout)findViewById(R.id.ll_previsou);
        tvPreviousContent = (TextView)findViewById(R.id.tv_previous_content);

        llPrevisou = (LinearLayout)findViewById(R.id.ll_previsou);
        tvPrevious = (TextView)findViewById(R.id.tv_previous);
        tvPreviousContent = (TextView)findViewById(R.id.tv_previous_content);

        llNext = (LinearLayout)findViewById(R.id.ll_next);
        tvNext = (TextView)findViewById(R.id.tv_next);
        tvNextContent = (TextView)findViewById(R.id.tv_next_content);

        webView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.GONE);
                } else {
                    if (View.GONE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });

        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(null);
        // 设置返回按钮
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initData() {
        if (articlesList == null) {
            ToastAlone.showShortToast("无内容");
            return;
        }

        ArticlesBean.ArticlesListBean bean;
        bean = articlesList.get(currentPosition);
        tvTitle.setText(bean.getTitle());
        tvTime.setText(TimeUtil.millisecond2DateStr(bean.getCreateTime()));
        webView.loadDataWithBaseURL(null,bean.getContent(),"text/html", "utf-8", null);

        // 设置上一篇下一篇按钮状态
        if (articlesList.size() == 1) {
            llPrevisou.setVisibility(View.GONE);
            tvPrevious.setTextColor(getResources().getColor(R.color.background_gray));
            tvPreviousContent.setTextColor(getResources().getColor(R.color.background_gray));
            tvPreviousContent.setText("");

            llNext.setVisibility(View.GONE);
            tvNext.setTextColor(getResources().getColor(R.color.background_gray));
            tvNextContent.setTextColor(getResources().getColor(R.color.background_gray));
            tvNextContent.setText("");
        }else{
            bean = articlesList.get(currentPosition + 1);
            llPrevisou.setVisibility(View.GONE);
            tvPrevious.setTextColor(getResources().getColor(R.color.background_gray));
            tvPreviousContent.setTextColor(getResources().getColor(R.color.background_gray));
            tvPreviousContent.setText("");

            llNext.setVisibility(View.VISIBLE);
            tvNext.setTextColor(getResources().getColor(R.color.red));
            tvNextContent.setTextColor(getResources().getColor(R.color.red));
            tvNextContent.setText(bean.getTitle());
        }

        /*String data = AssetsUtil.readFile("html3.txt");
        webView.loadDataWithBaseURL(null,data,"text/html", "utf-8", null);*/

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

    @Override
    public void onClick(View v) {
        ArticlesBean.ArticlesListBean bean;
        switch (v.getId()) {
            case R.id.ll_previsou:
                if (currentPosition > 0) {
                    currentPosition--;
                    bean = articlesList.get(currentPosition);
                    tvTitle.setText(bean.getTitle());
                    tvTime.setText(TimeUtil.millisecond2DateStr(bean.getCreateTime()));
                    webView.loadDataWithBaseURL(null,bean.getContent(),"text/html", "utf-8", null);
                }
                break;

            case R.id.ll_next:
                if (articlesList != null && currentPosition < articlesList.size() - 1) {
                    currentPosition++;
                    bean = articlesList.get(currentPosition);
                    tvTitle.setText(bean.getTitle());
                    tvTime.setText(TimeUtil.millisecond2DateStr(bean.getCreateTime()));
                    webView.loadDataWithBaseURL(null,bean.getContent(),"text/html", "utf-8", null);
                }
                break;
        }
        setPreviousAndNext();
    }

    /**
     * 改变"上一篇"和"下一篇"按钮颜色
     */
    private void setPreviousAndNext() {
        if(articlesList == null || articlesList.size() == 1){
            return;
        }
        ArticlesBean.ArticlesListBean bean1;
        ArticlesBean.ArticlesListBean bean2;
        String title = "";
        if(currentPosition == 0){
            // 首页
            if(articlesList.size() > 1){
                bean1 = articlesList.get(currentPosition + 1);
                title = bean1.getTitle();
            }
            llPrevisou.setVisibility(View.GONE);
            tvPrevious.setTextColor(getResources().getColor(R.color.background_gray));
            tvPreviousContent.setTextColor(getResources().getColor(R.color.background_gray));
            tvPreviousContent.setText("");

            llNext.setVisibility(View.VISIBLE);
            tvNext.setTextColor(getResources().getColor(R.color.red));
            tvNextContent.setTextColor(getResources().getColor(R.color.red));
            tvNextContent.setText(title);

        }else if(currentPosition == articlesList.size() - 1){
            // 尾页
            bean1 = articlesList.get(currentPosition - 1);
            title = bean1.getTitle();

            llPrevisou.setVisibility(View.VISIBLE);
            tvPrevious.setTextColor(getResources().getColor(R.color.red));
            tvPreviousContent.setTextColor(getResources().getColor(R.color.red));
            tvPreviousContent.setText(title);

            llNext.setVisibility(View.GONE);
            tvNext.setTextColor(getResources().getColor(R.color.background_gray));
            tvNextContent.setTextColor(getResources().getColor(R.color.background_gray));
            tvNextContent.setText("");

        }else{
            // 中间页
            bean1 = articlesList.get(currentPosition - 1);
            bean2 = articlesList.get(currentPosition + 1);

            llPrevisou.setVisibility(View.VISIBLE);
            tvPrevious.setTextColor(getResources().getColor(R.color.red));
            tvPreviousContent.setTextColor(getResources().getColor(R.color.red));
            tvPreviousContent.setText(bean1.getTitle());

            llNext.setVisibility(View.VISIBLE);
            tvNext.setTextColor(getResources().getColor(R.color.red));
            tvNextContent.setTextColor(getResources().getColor(R.color.red));
            tvNextContent.setText(bean2.getTitle());
        }
    }
}
