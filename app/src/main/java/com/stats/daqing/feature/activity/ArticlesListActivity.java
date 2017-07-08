package com.stats.daqing.feature.activity;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.stats.daqing.R;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.ArticlesBean;
import com.stats.daqing.feature.adapter.ArticlesAdapter;

import java.util.ArrayList;


/**
 * 数据发布 - 文章列表
 */
public class ArticlesListActivity extends BaseActivity implements View.OnClickListener {


    private RecyclerView rvContent;


    private ArrayList<ArticlesBean.ArticlesListBean> articlesList;
    private TintToolbar mToolBar;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles_list);

        revMsg();
        initView();
    }


    private void revMsg() {
        Intent intent = getIntent();
        articlesList = intent.getParcelableArrayListExtra("articlesList");
        title = intent.getStringExtra("title");
    }


    private void initView() {
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        rvContent = (RecyclerView) findViewById(R.id.rv_content);


        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
            // 设置返回按钮
            supportActionBar.setHomeButtonEnabled(true);
        }

        ArticlesAdapter mAdapter = new ArticlesAdapter(this,articlesList);
        rvContent.setLayoutManager(new LinearLayoutManager(ArticlesListActivity.this));
        rvContent.setAdapter(mAdapter);
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
        switch (v.getId()) {
            case R.id.rl_item:
                Intent intent = new Intent(ArticlesListActivity.this, ArticlesActivity.class);
                intent.putParcelableArrayListExtra("articlesList", new ArrayList<Parcelable>(articlesList));
                startActivity(intent);
                break;
        }
    }
}
