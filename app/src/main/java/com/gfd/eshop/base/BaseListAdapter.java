package com.gfd.eshop.base;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 简单列表适配器基类.
 *
 * @param <T> 数据实体的类型.
 * @param <V> ViewHolder的类型.
 */
public abstract class BaseListAdapter<T, V extends BaseListAdapter.ViewHolder> extends BaseAdapter {

    private final List<T> mDataSet = new ArrayList<>();


    @Override public final int getCount() {
        return mDataSet.size();
    }

    @Override public final T getItem(int position) {
        return mDataSet.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public final View getView(int position, View convertView, ViewGroup parent) {
        View itemView = createItemViewIfNotExist(convertView, parent);
        // noinspection unchecked
        ViewHolder viewHolder = (ViewHolder) itemView.getTag();
        viewHolder.bind(position);
        return itemView;
    }

    /**
     * 更新数据
     * @param data
     */
    public void reset(@Nullable List<T> data) {
        mDataSet.clear();
        if (data != null) mDataSet.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     * @param data
     */
    public void addAll(@Nullable List<T> data) {
        if (data != null) mDataSet.addAll(data);
        notifyDataSetChanged();
    }

    @LayoutRes
    protected abstract int getItemViewLayout();

    protected abstract V getItemViewHolder(View itemView);

    private View createItemViewIfNotExist(View itemView, ViewGroup parent) {
        if (itemView == null) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(getItemViewLayout(), parent, false);
            itemView.setTag(getItemViewHolder(itemView));
        }
        return itemView;
    }

    public abstract class ViewHolder {

        final View mItemView;

        protected ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
            this.mItemView = itemView;
        }

        protected final Context getContext() {
            return mItemView.getContext();
        }

        /**
         * 设置item数据
         * @param position
         */
        protected abstract void bind(int position);
    }

}
