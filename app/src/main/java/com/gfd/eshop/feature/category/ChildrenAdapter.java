package com.gfd.eshop.feature.category;


import android.view.View;
import android.widget.TextView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseListAdapter;
import com.gfd.eshop.network.entity.CategoryBase;

import butterknife.BindView;

/**
 * 二级分类列表Adapter
 */
public class ChildrenAdapter extends BaseListAdapter<CategoryBase, ChildrenAdapter.ViewHolder> {

    @Override protected int getItemViewLayout() {
        return R.layout.item_children_category;
    }

    @Override protected ViewHolder getItemViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder {

        @BindView(R.id.text_category) TextView tvCategory;

        ViewHolder(View itemView) {
            super(itemView);
        }

        @Override protected void bind(int position) {
            tvCategory.setText(getItem(position).getName());
        }
    }

}
