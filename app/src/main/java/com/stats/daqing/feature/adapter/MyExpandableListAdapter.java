package com.stats.daqing.feature.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.stats.daqing.R;
import com.stats.daqing.bean.DataReleaseBean;
import com.stats.daqing.common.ToastAlone;
import com.stats.daqing.feature.activity.ArticleActivity;

import java.util.ArrayList;
import java.util.List;

public class MyExpandableListAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter, View.OnClickListener {

    private List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> dataList;
    private Context mContext;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            notifyDataSetChanged();//更新数据
            super.handleMessage(msg);
        }
    };

    /*private String[] armTypes = new String[]{
            "WORD", "EXCEL", "EMAIL", "PPT",
            "WORD", "EXCEL", "EMAIL", "PPT",
            "WORD", "EXCEL", "EMAIL", "PPT",
            "WORD", "EXCEL", "EMAIL", "PPT",
            "WORD", "EXCEL", "EMAIL", "PPT"
    };

    private String[][] arms = new String[][]{
            {"文档编辑", "文档排版", "文档处理", "文档打印"},
            {"表格编辑", "表格排版", "表格处理", "表格打印"},
            {"收发邮件", "管理邮箱", "登录登出", "注册绑定"},
            {"演示编辑", "演示排版", "演示处理", "演示打印"},

            {"文档编辑", "文档排版", "文档处理", "文档打印"},
            {"表格编辑", "表格排版", "表格处理", "表格打印"},
            {"收发邮件", "管理邮箱", "登录登出", "注册绑定"},
            {"演示编辑", "演示排版", "演示处理", "演示打印"},

            {"文档编辑", "文档排版", "文档处理", "文档打印"},
            {"表格编辑", "表格排版", "表格处理", "表格打印"},
            {"收发邮件", "管理邮箱", "登录登出", "注册绑定"},
            {"演示编辑", "演示排版", "演示处理", "演示打印"},

            {"文档编辑", "文档排版", "文档处理", "文档打印"},
            {"表格编辑", "表格排版", "表格处理", "表格打印"},
            {"收发邮件", "管理邮箱", "登录登出", "注册绑定"},
            {"演示编辑", "演示排版", "演示处理", "演示打印"},

            {"文档编辑", "文档排版", "文档处理", "文档打印"},
            {"表格编辑", "表格排版", "表格处理", "表格打印"},
            {"收发邮件", "管理邮箱", "登录登出", "注册绑定"},
            {"演示编辑", "演示排版", "演示处理", "演示打印"},
    };*/


    public MyExpandableListAdapter(Context context, List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> dataList) {
        this.mContext = context;
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        this.dataList = dataList;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataList.get(groupPosition).second.size();
        // return arms[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).second.get(childPosition);

        // return arms[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_release_group, null);
            groupHolder = new GroupHolder();
            groupHolder.tvGroupName = (TextView) convertView.findViewById(R.id.tv_group_name);
            groupHolder.ivArrow = (ImageView) convertView.findViewById(R.id.iv_arrow);
            convertView.setTag(groupHolder);

        }else{
            groupHolder = (GroupHolder) convertView.getTag();
        }

        // groupHolder.tvGroupName.setText(getGroup(groupPosition).toString());

        Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>> pair = dataList.get(groupPosition);
        DataReleaseBean.TypesListBean bean = pair.first;
        groupHolder.tvGroupName.setText(bean.getTypeName());
        groupHolder.ivArrow.setSelected(isExpanded);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_release_child, null);
            childHolder = new ChildHolder();
            childHolder.tvChildName = (TextView) convertView.findViewById(R.id.tv_child_name);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildHolder) convertView.getTag();
        }

        // childHolder.tvChildName.setText(getChild(groupPosition, childPosition).toString());


        DataReleaseBean.TypesListBean bean = dataList.get(groupPosition).second.get(childPosition);
        childHolder.tvChildName.setText(bean.getTypeName());
        childHolder.tvChildName.setTag(bean);
        childHolder.tvChildName.setOnClickListener(this);
        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public void rotateArrow(ImageView mArrowImageView) {
        long duration = 300;
        int degree = 0;
        if (mArrowImageView.getTag() == null || mArrowImageView.getTag().equals(true)) {
            mArrowImageView.setTag(false);
            degree = -180;
        } else {
            degree = 0;
            mArrowImageView.setTag(true);
        }
        mArrowImageView.animate().setDuration(duration).rotation(degree);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }


    /*供外界更新数据的方法*/
    public void refresh(ExpandableListView expandableListView, int groupPosition){
        handler.sendMessage(new Message());
        //必须重新伸缩之后才能更新数据
        expandableListView.collapseGroup(groupPosition);
        expandableListView.expandGroup(groupPosition);
    }




    public void setData(List<Pair<DataReleaseBean.TypesListBean, List<DataReleaseBean.TypesListBean>>> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_child_name:

                DataReleaseBean.TypesListBean bean = (DataReleaseBean.TypesListBean) v.getTag();
                ToastAlone.showShortToast(bean.getTypeName());
                Intent intent = new Intent(mContext, ArticleActivity.class);
                mContext.startActivity(intent);
                break;
        }
    }


    class GroupHolder{
        TextView tvGroupName;
        ImageView ivArrow;

    }

    class ChildHolder{
        TextView tvChildName;
    }





}