package com.stats.daqing.feature.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stats.daqing.R;
import com.stats.daqing.base.BasePager;
import com.stats.daqing.bean.DataInterpretationBean;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.feature.activity.DataDetailsActivity;
import com.stats.daqing.feature.adapter.DataInterpretationAdapter;
import com.stats.daqing.feature.adapter.DataQueryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据查询
 * Created by Administrator on 2017/5/21.
 */

public class DataQueryFragment extends BasePager implements View.OnClickListener {


    private XRecyclerView mRecyclerView;
    private DataQueryAdapter mAdapter;
    private List<DataInterpretationBean> listData;
    private int refreshTime = 0;
    private int times = 0;

    public DataQueryFragment(Context context) {
        super(context);
        initData();
    }

    @Override
    public View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_data_query, null);
        mRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recyclerview);
        return inflate;
    }

    @Override
    public void initData() {

        listData = getData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                refreshTime ++;
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mAdapter.setData(getData());
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }


            @Override
            public void onLoadMore() {
                if(times < 2){
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            mAdapter.addData(getData());
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRecyclerView.setNoMore(true);
                            mAdapter.addData(getData());
                        }
                    }, 1000);
                }
                times++;
            }

        });

        mAdapter = new DataQueryAdapter(this,listData);
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<DataInterpretationBean> getData() {
        List<DataInterpretationBean> data = new ArrayList<DataInterpretationBean>();
        String[] titles = mContext.getResources().getStringArray(R.array.data_interpretation_titles);
        String[] times = mContext.getResources().getStringArray(R.array.data_interpretation_time);
        String[] urls = mContext.getResources().getStringArray(R.array.data_interpretation_urls);
        String[] imgUrls = mContext.getResources().getStringArray(R.array.data_interpretation_img_urls);

        DataInterpretationBean bean;
        for(int i = 0; i < titles.length ;i++){
            bean = new DataInterpretationBean();
            bean.setTitle(titles[i]);
            bean.setCreatetime(times[i]);
            bean.setUrl(urls[i]);
            // bean.setImgResId(R.drawable.home_01);
            bean.setImgUrl(imgUrls[i]);
            data.add(bean);
        }
        return data;
    }


    @Override
    public void onClick(View v) {
        DataInterpretationBean bean;
        switch (v.getId()) {
            case R.id.rl_item:
                bean = (DataInterpretationBean) v.getTag();
                Intent intent = new Intent(mContext, DataDetailsActivity.class);
                intent.putExtra("DataDetails", bean);
                mContext.startActivity(intent);
                break;

            case R.id.tv_down:
                // 下载
                bean = (DataInterpretationBean) v.getTag();
                ToastAlone.showShortToast("下载文件:" + bean.getTitle());
                break;
        }
    }

}
