package com.stats.daqing.feature.fragment;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.stats.daqing.R;
import com.stats.daqing.base.BasePager;
import com.stats.daqing.bean.DataReleaseBean;
import com.stats.daqing.bean.DataReleaseTreeBean;
import com.stats.daqing.common.Urls;
import com.stats.daqing.feature.adapter.MyExpandableListAdapter;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据发布
 * Created by Administrator on 2017/5/21.
 */

public class DataRelease2Fragment extends BasePager implements View.OnClickListener {

    private ExpandableListView elvContent;
    private RelativeLayout rlMonth;
    private View vLine1;
    private TextView tvMonth;
    private RelativeLayout rlSeason;
    private View vLine2;
    private TextView tvSeason;
    private RelativeLayout rlYear;
    private View vLine3;
    private TextView tvYear;
    private DataReleaseTreeBean dataReleaseTree;
    private MyExpandableListAdapter mAdapter;


    public DataRelease2Fragment(Context context) {
        super(context);
        initData();
    }

    @Override
    public View initView() {
        View inflate = View.inflate(mContext, R.layout.fragment_data_release2, null);
        assignViews(inflate);
        setListener();
        return inflate;
    }


    @Override
    public void initData() {
        getData();

    }

    private void getData() {
        /*String json = "{\"counts\":36,\"currentPage\":1,\"pageSize\":20,\"totalPage\":2,\"typesList\":[{\"createTime\":1496226554000,\"createUser\":\"18210281168\",\"id\":36,\"isShow\":0,\"parentId\":32,\"typeName\":\"全省各市（地）地区生产总值\"},{\"createTime\":1496226541000,\"createUser\":\"18210281168\",\"id\":35,\"isShow\":0,\"parentId\":32,\"typeName\":\"各县区地区生产总值\"},{\"createTime\":1496226530000,\"createUser\":\"18210281168\",\"id\":34,\"isShow\":0,\"parentId\":32,\"typeName\":\"分行业地区生产总值\"},{\"createTime\":1496226516000,\"createUser\":\"18210281168\",\"id\":33,\"isShow\":0,\"parentId\":32,\"typeName\":\"地区生产总值主要指标\"},{\"createTime\":1496226493000,\"createUser\":\"18210281168\",\"id\":32,\"isShow\":0,\"parentId\":4,\"typeName\":\"GDP\"},{\"createTime\":1496226458000,\"createUser\":\"18210281168\",\"id\":31,\"isShow\":0,\"parentId\":28,\"typeName\":\"畜禽出栏及产品产量\"},{\"createTime\":1496226442000,\"createUser\":\"18210281168\",\"id\":30,\"isShow\":0,\"parentId\":28,\"typeName\":\"畜禽存栏\"},{\"createTime\":1496226430000,\"createUser\":\"18210281168\",\"id\":29,\"isShow\":0,\"parentId\":28,\"typeName\":\"农林牧渔业增加值\"},{\"createTime\":1496226408000,\"createUser\":\"18210281168\",\"id\":28,\"isShow\":0,\"parentId\":4,\"typeName\":\"农业\"},{\"createTime\":1496226344000,\"createUser\":\"18210281168\",\"id\":27,\"isShow\":0,\"parentId\":21,\"typeName\":\"规上工业主要产品产量及用电量\"},{\"createTime\":1496226311000,\"createUser\":\"18210281168\",\"id\":26,\"isShow\":0,\"parentId\":21,\"typeName\":\"未生产企业名单\"},{\"createTime\":1496226247000,\"createUser\":\"18210281168\",\"id\":25,\"isShow\":0,\"parentId\":21,\"typeName\":\"规上工业工业总产值增长面\"},{\"createTime\":1496226234000,\"createUser\":\"18210281168\",\"id\":24,\"isShow\":0,\"parentId\":21,\"typeName\":\"规上工业主要效益指标\"},{\"createTime\":1496226219000,\"createUser\":\"18210281168\",\"id\":23,\"isShow\":0,\"parentId\":21,\"typeName\":\"地方规上工业十大产业增加值\"},{\"createTime\":1496226190000,\"createUser\":\"18210281168\",\"id\":22,\"isShow\":0,\"parentId\":21,\"typeName\":\"规上工业增加值\"},{\"createTime\":1496226166000,\"createUser\":\"18210281168\",\"id\":21,\"isShow\":0,\"parentId\":3,\"typeName\":\"工业和能源\"},{\"createTime\":1496226124000,\"createUser\":\"18210281168\",\"id\":20,\"isShow\":0,\"parentId\":19,\"typeName\":\"建筑业主要指标\"},{\"createTime\":1496226080000,\"createUser\":\"18210281168\",\"id\":19,\"isShow\":0,\"parentId\":4,\"typeName\":\"建筑业\"},{\"createTime\":1496226042000,\"createUser\":\"18210281168\",\"id\":18,\"isShow\":0,\"parentId\":15,\"typeName\":\"房地产开发主要指标\"},{\"createTime\":1496226027000,\"createUser\":\"18210281168\",\"id\":17,\"isShow\":0,\"parentId\":15,\"typeName\":\"分县区固定资产投资\"}]}";
        Gson gson = new Gson();
        DataReleaseBean bean = gson.fromJson(json, DataReleaseBean.class);

        List<DataReleaseBean.TypesListBean> list = bean.getTypesList();

        dataReleaseTree = new DataReleaseTreeBean();
        List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> monthDataList = new ArrayList<>();
        List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> seasonDataList = new ArrayList<>();
        List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> yearDataList = new ArrayList<>();
        DataReleaseBean.TypesListBean typeBean;
        for (int i = 0; i < list.size(); i++) {
            typeBean = list.get(i);
            if (typeBean.getParentId() == 3) {
                List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                monthDataList.add(new Pair<>(typeBean, typesList));

            }else if (typeBean.getParentId() == 4){
                List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                seasonDataList.add(new Pair<>(typeBean, typesList));

            }else if (typeBean.getParentId() == 5){
                List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                yearDataList.add(new Pair<>(typeBean, typesList));
            }
        }

        dataReleaseTree.setMonthDataList(monthDataList);
        dataReleaseTree.setSeasonDataList(seasonDataList);
        dataReleaseTree.setYearDataList(yearDataList);
        showData(1,dataReleaseTree.getMonthDataList());*/



        x.http().get(new RequestParams(Urls.URL_APP_TYPES), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                DataReleaseBean bean = gson.fromJson(result, DataReleaseBean.class);

                List<DataReleaseBean.TypesListBean> list = bean.getTypesList();

                dataReleaseTree = new DataReleaseTreeBean();
                List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> monthDataList = new ArrayList<>();
                List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> seasonDataList = new ArrayList<>();
                List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> yearDataList = new ArrayList<>();
                DataReleaseBean.TypesListBean typeBean;
                for (int i = 0; i < list.size(); i++) {
                    typeBean = list.get(i);
                    if (typeBean.getParentId() == 3) {
                        List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                        monthDataList.add(new Pair<>(typeBean, typesList));

                    }else if (typeBean.getParentId() == 4){
                        List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                        seasonDataList.add(new Pair<>(typeBean, typesList));

                    }else if (typeBean.getParentId() == 5){
                        List<DataReleaseBean.TypesListBean> typesList = filterData(list, typeBean);
                        yearDataList.add(new Pair<>(typeBean, typesList));
                    }
                }

                dataReleaseTree.setMonthDataList(monthDataList);
                dataReleaseTree.setSeasonDataList(seasonDataList);
                dataReleaseTree.setYearDataList(yearDataList);
                showData(1,dataReleaseTree.getMonthDataList());

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

    private List<DataReleaseBean.TypesListBean> filterData(List<DataReleaseBean.TypesListBean> list, DataReleaseBean.TypesListBean typeBean) {

        DataReleaseBean.TypesListBean bean;
        List<DataReleaseBean.TypesListBean> typeList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            bean = list.get(i);
            if(bean.getParentId() == typeBean.getId()){
                typeList.add(bean);
            }
        }
        return typeList;
    }


    private void assignViews(View view) {
        elvContent = (ExpandableListView) view.findViewById(R.id.elv_content);
        rlMonth = (RelativeLayout) view.findViewById(R.id.rl_month);
        vLine1 = view.findViewById(R.id.v_line1);
        tvMonth = (TextView) view.findViewById(R.id.tv_month);
        rlSeason = (RelativeLayout) view.findViewById(R.id.rl_season);
        vLine2 = view.findViewById(R.id.v_line2);
        tvSeason = (TextView) view.findViewById(R.id.tv_season);
        rlYear = (RelativeLayout) view.findViewById(R.id.rl_year);
        vLine3 = view.findViewById(R.id.v_line3);
        tvYear = (TextView) view.findViewById(R.id.tv_year);

        elvContent = (ExpandableListView) view.findViewById(R.id.elv_content);
        mAdapter = new MyExpandableListAdapter(mContext,null);
        elvContent.setAdapter(mAdapter);
    }

    private void setListener() {
        rlMonth.setOnClickListener(this);
        rlSeason.setOnClickListener(this);
        rlYear.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_month:
                // 月度数据
                showData(1,dataReleaseTree.getMonthDataList());
                break;
            case R.id.rl_season:
                // 季度数据
                showData(2,dataReleaseTree.getSeasonDataList());
                break;
            case R.id.rl_year:
                // 年度数据
                showData(3,dataReleaseTree.getYearDataList());
                break;
        }
    }

    /**
     * 显示数据
     * @param i 1:月度数据  2:季度数据  3:年度数据
     * @param monthDataList
     */
    private void showData(int i, List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> monthDataList) {
        setBackround(i);
        mAdapter = new MyExpandableListAdapter(mContext,monthDataList);
        elvContent.setAdapter(mAdapter);
    }

    private void setBackround(int index) {
        vLine1.setSelected(index == 1);
        vLine2.setSelected(index == 2);
        vLine3.setSelected(index == 3);
    }

}
