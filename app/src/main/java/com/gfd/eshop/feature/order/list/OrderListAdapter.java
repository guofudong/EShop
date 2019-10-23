package com.gfd.eshop.feature.order.list;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseListAdapter;
import com.gfd.eshop.base.glide.GlideUtils;
import com.gfd.eshop.feature.goods.GoodsActivity;
import com.gfd.eshop.network.api.ApiOrderList;
import com.gfd.eshop.network.entity.Order;
import com.gfd.eshop.network.entity.OrderGoods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单列表适配器
 */
public abstract class OrderListAdapter extends BaseListAdapter<Order, OrderListAdapter.ViewHolder> {

    @ApiOrderList.OrderType private final String mType;

    OrderListAdapter(@ApiOrderList.OrderType String type) {
        mType = type;
    }

    @Override protected int getItemViewLayout() {
        return R.layout.item_order;
    }

    @Override protected ViewHolder getItemViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    protected abstract void onOrderCancel(Order order);

    protected abstract void onOrderPurchase(Order order);

    class ViewHolder extends BaseListAdapter.ViewHolder {

        @BindView(R.id.text_order_sn) TextView tvOrderSn;
        @BindView(R.id.text_order_time) TextView tvOrderTime;
        @BindView(R.id.text_subtotal) TextView tvSubtotal;
        @BindView(R.id.button_cancel) Button btnCancel;
        @BindView(R.id.button_purchase) Button btnPurchase;
        @BindView(R.id.layout_goods) LinearLayout goodsLayout;

        private Order mOrder;

        ViewHolder(View itemView) {
            super(itemView);
            switch (mType) {
                case ApiOrderList.ORDER_AWAIT_PAY:
                    btnCancel.setVisibility(View.VISIBLE);
                    btnPurchase.setVisibility(View.VISIBLE);
                    break;
                case ApiOrderList.ORDER_UNCONFIRMED:
                    btnCancel.setVisibility(View.VISIBLE);
                    break;
            }
        }

        @Override protected void bind(int position) {
            mOrder = getItem(position);
            String orderSn = getContext().getString(R.string.order_sn, mOrder.getSn());
            tvOrderSn.setText(orderSn);
            String orderTime = getContext().getString(R.string.order_time, mOrder.getTime());
            tvOrderTime.setText(orderTime);
            String subTotal = getContext().getString(R.string.order_total, mOrder.getTotalFee());
            tvSubtotal.setText(subTotal);
            goodsLayout.removeAllViews();
            List<OrderGoods> goodsList = mOrder.getGoodsList();
            for (OrderGoods goods : goodsList) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_order_list_goods, goodsLayout, false);
                GoodsItemHolder holder = new GoodsItemHolder(view);
                holder.bind(goods);
                goodsLayout.addView(view);
            }
        }

        @OnClick({R.id.button_cancel, R.id.button_purchase}) void onClick(View view) {
            if (view.getId() == R.id.button_cancel) {
                onOrderCancel(mOrder);
            } else {
                onOrderPurchase(mOrder);
            }
        }

        class GoodsItemHolder {
            @BindView(R.id.text_goods_name) TextView tvName;
            @BindView(R.id.text_amount) TextView tvAmount;
            @BindView(R.id.image_goods) ImageView ivGoods;

            private OrderGoods mOrderGoods;
            GoodsItemHolder(View view) {
                ButterKnife.bind(this, view);
            }
            @SuppressLint("StringFormatMatches")
            public void bind(OrderGoods orderGoods) {
                mOrderGoods = orderGoods;
                tvName.setText(orderGoods.getGoodsName());
                int number = mOrderGoods.getGoodsNumber();
                tvAmount.setText(getContext().getString(R.string.order_goods_amount, number));
                GlideUtils.loadPicture(mOrderGoods.getImg(), ivGoods);
            }
            @OnClick void onClick() {
                Intent intent = GoodsActivity
                        .getStartIntent(getContext(), mOrderGoods.getGoodsId());
                getContext().startActivity(intent);
            }
        }
    }

}
