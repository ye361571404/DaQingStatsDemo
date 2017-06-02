package com.stats.daqing.feature.adapter;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stats.daqing.base.BasePager;

import java.util.List;

/**
 * Created by hackware on 2016/9/10.
 */

public class ExamplePagerAdapter extends PagerAdapter {
    private List<BasePager> mDataList;
    private List<String> titles;

    public ExamplePagerAdapter(List<BasePager> dataList,List<String> titles) {
        this.mDataList = dataList;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView textView = new TextView(container.getContext());
        BasePager basePager = mDataList.get(position);
        container.addView(basePager.getRootView());
        return basePager.getRootView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /*@Override
    public int getItemPosition(Object object) {
        TextView textView = (TextView) object;
        String text = textView.getText().toString();
        int index = mDataList.indexOf(text);
        if (index >= 0) {
            return index;
        }
        return POSITION_NONE;
    }*/

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
