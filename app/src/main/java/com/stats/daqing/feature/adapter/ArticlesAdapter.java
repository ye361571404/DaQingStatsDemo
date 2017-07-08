package com.stats.daqing.feature.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stats.daqing.R;
import com.stats.daqing.bean.ArticlesBean;
import com.stats.daqing.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/9.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>{


    private View.OnClickListener onClickListener;
    private List<ArticlesBean.ArticlesListBean> articlesList;

    public ArticlesAdapter(View.OnClickListener onClickListener,List<ArticlesBean.ArticlesListBean> articlesList) {
        this.onClickListener = onClickListener;
        if (articlesList == null) {
            articlesList = new ArrayList<>();
        }
        this.articlesList = articlesList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(parent.getContext(), R.layout.item_artcles_list, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ArticlesBean.ArticlesListBean bean = articlesList.get(position);

        holder.rlItem.setOnClickListener(onClickListener);
        holder.tvName.setText(bean.getTitle());
        holder.tvTime.setText(TimeUtil.millisecond2DateStr(bean.getCreateTime()));
    }

    @Override
    public int getItemCount() {
        return articlesList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rlItem;
        TextView tvTime;
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            rlItem = (RelativeLayout) itemView.findViewById(R.id.rl_item);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }


}
