package com.gfd.eshop.feature.search;


import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseListAdapter;
import com.gfd.eshop.base.glide.GlideUtils;
import com.gfd.eshop.network.entity.Picture;
import com.gfd.eshop.network.entity.SimpleGoods;

import butterknife.BindView;

/**
 * 搜索商品Adapter
 */
public class SearchGoodsAdapter extends BaseListAdapter<SimpleGoods, SearchGoodsAdapter.ViewHolder> {

    @Override protected int getItemViewLayout() {
        return R.layout.item_search_goods;
    }

    @Override protected ViewHolder getItemViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    class ViewHolder extends BaseListAdapter.ViewHolder {

        @BindView(R.id.image_goods) ImageView ivGoods;
        @BindView(R.id.text_goods_name) TextView tvName;
        @BindView(R.id.text_goods_price) TextView tvPrice;
        @BindView(R.id.text_market_price) TextView tvMarketPrice;

        private SimpleGoods mItem;

        ViewHolder(View itemView) {
            super(itemView);
        }

        @Override protected void bind(int position) {
            mItem = getItem(position);
            tvName.setText(mItem.getName());
            tvPrice.setText(mItem.getShopPrice());
            // 设置商场价格, 并添加删除线
            String marketPrice = mItem.getMarketPrice();
            SpannableString spannableString = new SpannableString(marketPrice);
            spannableString.setSpan(new StrikethroughSpan(), 0, marketPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvMarketPrice.setText(spannableString);
            Picture picture = mItem.getImg();
            GlideUtils.loadPicture(picture, ivGoods);
        }
    }

}
