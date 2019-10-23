package com.gfd.eshop.feature.collect;


import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.glide.GlideUtils;
import com.gfd.eshop.feature.goods.GoodsActivity;
import com.gfd.eshop.network.entity.CollectGoods;
import com.gfd.eshop.network.entity.Picture;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * 收藏商品页面Adapter
 */
public abstract class CollectGoodsAdapter extends RecyclerView.Adapter<CollectGoodsAdapter.ViewHolder> {

    static final int TYPE_LIST = R.layout.item_collect_goods_list;
    static final int TYPE_GRID = R.layout.item_collect_goods_grid;

    @IntDef({TYPE_LIST, TYPE_GRID})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type { }

    @Type private int mType = TYPE_LIST;

    private final List<CollectGoods> mDataSet = new ArrayList<>();

    @Override
    public CollectGoodsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(CollectGoodsAdapter.ViewHolder holder, int position) {
        holder.bind(mDataSet.get(position));
    }

    @Override public int getItemCount() {
        return mDataSet.size();
    }

    @Override public int getItemViewType(int position) {
        return mType;
    }

    void setType(@Type int type) {
        mType = type;
        notifyDataSetChanged();
    }

    public void reset(@Nullable List<CollectGoods> data) {
        mDataSet.clear();
        if (data != null) mDataSet.addAll(data);
        notifyDataSetChanged();
    }

    public abstract void onLongClicked(CollectGoods collectGoods);

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_goods) ImageView ivGoods;
        @BindView(R.id.text_goods_name) TextView tvName;
        @BindView(R.id.text_goods_price) TextView tvPrice;
        @BindView(R.id.text_market_price) TextView tvMarketPrice;

        private CollectGoods mItem;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void bind(CollectGoods collectGoods) {
            mItem = collectGoods;
            tvName.setText(mItem.getName());
            tvPrice.setText(mItem.getShopPrice());
            // 设置商场价格, 并添加删除线
            String marketPrice = mItem.getMarketPrice();
            SpannableString spannableString = new SpannableString(marketPrice);
            spannableString.setSpan(new StrikethroughSpan(), 0, marketPrice.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvMarketPrice.setText(spannableString);
            Picture picture = mItem.getImg();
            GlideUtils.loadPicture(picture, ivGoods);
        }

        @OnClick void onClick() {
            Context context = itemView.getContext();
            Intent intent = GoodsActivity.getStartIntent(context, mItem.getId());
            context.startActivity(intent);
        }

        @OnLongClick boolean onLongClick() {
            onLongClicked(mItem);
            return true;
        }
    }

}
