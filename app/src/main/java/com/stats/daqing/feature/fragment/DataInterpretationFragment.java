package com.stats.daqing.feature.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stats.daqing.R;
import com.stats.daqing.base.BasePager;
import com.stats.daqing.bean.DataInterpretationBean;
import com.stats.daqing.feature.activity.DataDetailsActivity;
import com.stats.daqing.feature.adapter.DataInterpretationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据解读
 * Created by Administrator on 2017/5/21.
 */

public class DataInterpretationFragment extends BasePager implements View.OnClickListener {


    private XRecyclerView mRecyclerView;
    private DataInterpretationAdapter mAdapter;
    private List<DataInterpretationBean> listData;
    private int times = 0;


    public DataInterpretationFragment(Context context) {
        super(context);
        initData();
    }

    @Override
    public View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_data_interpretation, null);
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
                times = 0;
                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mAdapter.setData(getData());
                        mRecyclerView.refreshComplete();
                    }

                }, 1000);
            }


            @Override
            public void onLoadMore() {
                // 加载更多
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

        mAdapter = new DataInterpretationAdapter(this,listData);
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
        switch (v.getId()) {
            case R.id.rl_item:
                DataInterpretationBean bean = (DataInterpretationBean) v.getTag();
                Intent intent = new Intent(mContext, DataDetailsActivity.class);
                intent.putExtra("DataDetails", bean);
                mContext.startActivity(intent);
                break;
        }
    }

}
