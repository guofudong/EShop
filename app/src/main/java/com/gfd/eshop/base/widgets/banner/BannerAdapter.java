package com.gfd.eshop.base.widgets.banner;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.gfd.eshop.R;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BannerAdapter<T> extends PagerAdapter {

    private List<T> mDataSet = new ArrayList<>();

    private Queue<ViewHolder> mHolderQueue = new ArrayDeque<>();

    @Override public int getCount() {
        return mDataSet.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        ViewHolder holder = (ViewHolder) object;
        return view == holder.itemView;
    }

    @Override public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {

        ViewHolder holder = mHolderQueue.poll();

        if (holder == null) {
            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View itemView = inflater.inflate(R.layout.item_banner, container, false);
            holder = new ViewHolder(itemView);
        }

        container.addView(holder.itemView);
        bind(holder, mDataSet.get(position));
        return holder;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {

        ViewHolder holder = (ViewHolder) object;
        container.removeView(holder.itemView);
        mHolderQueue.add(holder);
    }

    public void reset(List<T> dataSet) {
        mDataSet.clear();
        mDataSet.addAll(dataSet);
        notifyDataSetChanged();
    }

    protected abstract void bind(ViewHolder holder, T data);

    public static class ViewHolder {

        @BindView(R.id.image_banner_item) public ImageView ivBannerItem;
        public View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }

}