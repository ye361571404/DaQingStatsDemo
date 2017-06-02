package com.stats.daqing.feature.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.stats.daqing.R;
import com.stats.daqing.bean.DataInterpretationBean;

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class DataQueryAdapter extends RecyclerView.Adapter<DataQueryAdapter.ViewHolder> {


    private View.OnClickListener onClickListener;
    private ImageLoader imageLoader;
    public List<DataInterpretationBean> datas = null;

    public DataQueryAdapter(View.OnClickListener onClickListener, List<DataInterpretationBean> datas) {
        this.onClickListener = onClickListener;
        this.datas = datas;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_data_query,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        DataInterpretationBean bean = datas.get(position);

        viewHolder.rlItem.setTag(bean);
        viewHolder.tvDown.setTag(bean);
        viewHolder.rlItem.setOnClickListener(onClickListener);
        viewHolder.tvDown.setOnClickListener(onClickListener);
        viewHolder.tvTitle.setText(bean.getTitle());
        viewHolder.tvCreateTime.setText(bean.getCreatetime());
        imageLoader.displayImage(bean.getImgUrl(),viewHolder.ivInfo);

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setData(List<DataInterpretationBean> data) {
        this.datas.clear();
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<DataInterpretationBean> data) {
        this.datas.addAll(data);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlItem;
        ImageView ivInfo;
        TextView tvTitle;
        TextView tvCreateTime;
        TextView tvDown;

        public ViewHolder(View view){
            super(view);
            rlItem = (RelativeLayout) view.findViewById(R.id.rl_item);
            ivInfo = (ImageView) view.findViewById(R.id.iv_info);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
            tvCreateTime = (TextView) view.findViewById(R.id.tv_create_time);
            tvDown = (TextView) view.findViewById(R.id.tv_down);
        }
    }






}
