package com.stats.daqing.feature.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stats.daqing.R;
import com.stats.daqing.base.BasePager;
import com.stats.daqing.bean.ArticlesBean;
import com.stats.daqing.bean.DataInterpretationBean;
import com.stats.daqing.bean.DataReleaseBean;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.common.Urls;
import com.stats.daqing.feature.activity.DataDetailsActivity;
import com.stats.daqing.feature.adapter.DataInterpretationAdapter;
import com.stats.daqing.feature.adapter.InterpretationTypeAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据解读
 * Created by Administrator on 2017/5/21.
 */

public class DataInterpretationFragment extends BasePager implements View.OnClickListener {


    private XRecyclerView mRecyclerView;
    private RecyclerView rvTypes;
    private DataInterpretationAdapter mAdapter;
    private List<DataInterpretationBean> listData;
    private int times = 0;
    private InterpretationTypeAdapter typeAdapter;
    private List<ArticlesBean.ArticlesListBean> articlesList;


    public DataInterpretationFragment(Context context) {
        super(context);
    }

    private void initVariable() {
        articlesList = new ArrayList<>();
    }

    @Override
    public View initView() {
        initVariable();

        View inflate = View.inflate(mContext, R.layout.fragment_data_interpretation, null);
        mRecyclerView = (XRecyclerView) inflate.findViewById(R.id.recyclerview);
        rvTypes = (RecyclerView) inflate.findViewById(R.id.rv_types);

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
                        // mAdapter.setData(getData());
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
                            // mAdapter.addData(getData());
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRecyclerView.setNoMore(true);
                            // mAdapter.addData(getData());
                        }
                    }, 1000);
                }
                times++;
            }

        });

        mAdapter = new DataInterpretationAdapter(this,articlesList);
        mRecyclerView.setAdapter(mAdapter);
        getTypes();
        return inflate;
    }



    private void getTypes() {
        RequestParams params = new RequestParams(Urls.URL_APP_TYPES);
        params.addParameter("columnId","2");

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                DataReleaseBean bean = gson.fromJson(result, DataReleaseBean.class);
                List<DataReleaseBean.TypesListBean> list = bean.getTypesList();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                rvTypes.setLayoutManager(linearLayoutManager);
                typeAdapter = new InterpretationTypeAdapter(DataInterpretationFragment.this, list);
                rvTypes.setAdapter(typeAdapter);
                getArticleData(list.get(0));
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

    /**
     * 获取文章
     * @param typeBean
     */
    private void getArticleData(DataReleaseBean.TypesListBean typeBean) {
        RequestParams entity = new RequestParams(Urls.URL_APP_ARTCLES);
        entity.addParameter("typeId",typeBean.getId());
        x.http().get(entity, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                ArticlesBean articlesBean = gson.fromJson(result, ArticlesBean.class);
                articlesList = articlesBean.getArticlesList();
                mAdapter.setData(articlesList);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastAlone.showShortToast("获取数据失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
                        // mAdapter.setData(getData());
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
                            // mAdapter.addData(getData());
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);

                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            mRecyclerView.setNoMore(true);
                            // mAdapter.addData(getData());
                        }
                    }, 1000);
                }
                times++;
            }

        });

        // mAdapter = new DataInterpretationAdapter(this,listData);
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
                // 进入文章详情
                ArticlesBean.ArticlesListBean bean = (ArticlesBean.ArticlesListBean) v.getTag();
                Intent intent = new Intent(mContext, DataDetailsActivity.class);
                intent.putExtra("articles", bean);
                mContext.startActivity(intent);
                break;

            case R.id.rl_type_item:
                // 点击类型,刷新文章列表
                int position = (int) v.getTag();
                DataReleaseBean.TypesListBean type = (DataReleaseBean.TypesListBean) v.getTag(R.id.rl_type_item);
                typeAdapter.setCurrentPosition(position);
                getArticleData(type);

                break;
        }
    }

}
