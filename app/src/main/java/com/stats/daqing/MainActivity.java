package com.stats.daqing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bilibili.magicasakura.widgets.TintToolbar;
import com.google.gson.Gson;
import com.stats.daqing.base.BaseActivity;
import com.stats.daqing.bean.ColumnsBean;
import com.stats.daqing.bean.MainItemBean;
import com.stats.daqing.common.ThemeHelper;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.feature.activity.BannerDataDetailsActivity;
import com.stats.daqing.feature.activity.DataActivity;
import com.stats.daqing.feature.activity.LoginActivity;
import com.stats.daqing.feature.adapter.ColumnAdapter;
import com.stats.daqing.feature.adapter.MainAdapter;
import com.stats.daqing.feature.banner.BannerImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener, OnBannerClickListener {

    private TintToolbar mToolBar;
    private Banner mBanner;
    private ArrayList images;
    private ArrayList titles;
    private RecyclerView rvContent;
    // private MainAdapter mAdapter;
    private ColumnAdapter mAdapter;
    private ArrayList<MainItemBean> items;
    private long mkeyTime;
    private String[] bannerUrls;
    private List<ColumnsBean.ColumnsListBean> columnsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVariable();
        initView();
        assignViews();
        initData();
        getData();
    }

    private void getData() {
        String url = "http://202.97.194.240:9080/app/column";
        x.http().get(new RequestParams(url), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("onSuccess: result = " + result);
                Gson gson = new Gson();
                ColumnsBean bean = gson.fromJson(result, ColumnsBean.class);
                columnsList = bean.getColumnsList();

                mAdapter.setData(columnsList);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void initData() {

        //默认是CIRCLE_INDICATOR
        mBanner.setImages(images)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new BannerImageLoader())
                .setOnBannerClickListener(this)
                .start();

        rvContent.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
        mAdapter = new ColumnAdapter(this,columnsList);
        rvContent.setAdapter(mAdapter);
    }

    private void initView() {
        // setTitle("");
        mToolBar = (TintToolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle(null);
        mBanner = (Banner) findViewById(R.id.banner);
        rvContent = (RecyclerView)findViewById(R.id.rv_content);
    }

    private void initVariable() {
        mCurrentTheme = ThemeHelper.getTheme(this);

        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);
        List list1 = Arrays.asList(tips);
        titles= new ArrayList(list1);
        bannerUrls = getResources().getStringArray(R.array.item_urls);

        items = new ArrayList<>();
        MainItemBean bean;
        String[] itemNames = getResources().getStringArray(R.array.item_names);

        // int[] itemIcons = getResources().getIntArray(R.array.item_icons);

        int[] itemIcons = new int[]{
                R.drawable.home_01,
                R.drawable.home_02,
                R.drawable.home_03,
                R.drawable.home_04,
                R.drawable.home_05,
        };

        for (int i = 0; i < itemNames.length; i++) {
            bean = new MainItemBean();
            bean.setName(itemNames[i]);
            bean.setImgResId(itemIcons[i]);
            items.add(bean);
        }

        columnsList = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 设置右上角按钮
        getMenuInflater().inflate(R.menu.user,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_user) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            ToastAlone.showShortToast("进入用户中心界面");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mBanner != null) {
            //开始轮播
            mBanner.startAutoPlay();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBanner != null) {
            //结束轮播
            mBanner.stopAutoPlay();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item:
                int position = (int) v.getTag();
                ToastAlone.showShortToast("onclick " + position);
                Intent intent = new Intent(MainActivity.this, DataActivity.class);
                intent.putExtra("position", position);
                intent.putParcelableArrayListExtra("items", items);
                startActivity(intent);
                break;

            case R.id.iv_pink:
                mCurrentTheme = ThemeHelper.CARD_SAKURA;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_purple:
                mCurrentTheme = ThemeHelper.CARD_HOPE;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_blue:
                mCurrentTheme = ThemeHelper.CARD_STORM;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_green:
                mCurrentTheme = ThemeHelper.CARD_WOOD;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_green_light:
                mCurrentTheme = ThemeHelper.CARD_LIGHT;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_yellow:
                mCurrentTheme = ThemeHelper.CARD_THUNDER;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_orange:
                mCurrentTheme = ThemeHelper.CARD_SAND;
                updateTheme(mCurrentTheme);
                break;
            case R.id.iv_red:
                mCurrentTheme = ThemeHelper.CARD_FIREY;
                updateTheme(mCurrentTheme);
                break;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mkeyTime) > 2000) {
                mkeyTime = System.currentTimeMillis();
                ToastAlone.showShortToast("再按一次返回键,将退出程序");
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    };

    @Override
    public void OnBannerClick(int position) {
        String url = bannerUrls[position - 1];
        Intent intent = new Intent(MainActivity.this, BannerDataDetailsActivity.class);
        intent.putExtra("bannerUrls", url);
        startActivity(intent);
        ToastAlone.showShortToast("positino = " + position);

    }


    private ImageView ivPink;
    private ImageView ivBlue;
    private ImageView ivPurple;
    private ImageView ivGreen;
    private ImageView ivGreenLight;
    private ImageView ivYellow;
    private ImageView ivOrange;
    private ImageView ivRed;
    private int mCurrentTheme;

    private void assignViews() {
        ivPink = (ImageView) findViewById(R.id.iv_pink);
        ivBlue = (ImageView) findViewById(R.id.iv_blue);
        ivPurple = (ImageView) findViewById(R.id.iv_purple);
        ivGreen = (ImageView) findViewById(R.id.iv_green);
        ivGreenLight = (ImageView) findViewById(R.id.iv_green_light);
        ivYellow = (ImageView) findViewById(R.id.iv_yellow);
        ivOrange = (ImageView) findViewById(R.id.iv_orange);
        ivRed = (ImageView) findViewById(R.id.iv_red);



        ivPink.setOnClickListener(this);
        ivBlue.setOnClickListener(this);
        ivPurple.setOnClickListener(this);
        ivGreen.setOnClickListener(this);
        ivGreenLight.setOnClickListener(this);
        ivYellow.setOnClickListener(this);
        ivOrange.setOnClickListener(this);
        ivRed.setOnClickListener(this);

    }













}
