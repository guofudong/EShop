package com.gfd.eshop.feature.order.preview;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.wrapper.AlertWrapper;
import com.gfd.eshop.base.wrapper.ProgressWrapper;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.feature.address.manage.ManageAddressActivity;
import com.gfd.eshop.feature.goods.GoodsActivity;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.api.ApiOrderDone;
import com.gfd.eshop.network.api.ApiOrderPreview;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.Address;
import com.gfd.eshop.network.entity.CartGoods;
import com.gfd.eshop.network.entity.Payment;
import com.gfd.eshop.network.entity.Shipping;
import com.gfd.eshop.network.event.UserEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 订单预览界面.
 */
public class OrderPreviewActivity extends BaseActivity {

    private static final String PAYMENT_CASH_ON_DELIVERY = "cod"; //货到付款

    @BindView(R.id.text_consignee)
    TextView tvConsignee;
    @BindView(R.id.text_address)
    TextView tvAddress;
    @BindView(R.id.layout_goods)
    LinearLayout goodsLayout;
    @BindView(R.id.text_payment)
    TextView tvPayment;
    @BindView(R.id.text_shipping)
    TextView tvShipping;
    @BindView(R.id.button_summit)
    Button btnSummit;
    @BindView(R.id.text_shipping_price)
    TextView tvShippingPrice;
    @BindView(R.id.text_goods_price)
    TextView tvGoodsPrice;


    private List<Payment> mPaymentList;
    private List<Shipping> mShippingList;
    private AlertWrapper mAlertWrapper;
    private ProgressWrapper mProgressWrapper;

    private int mPaymentId;
    private String mPaymentCode;
    private int mShippingId;

    @Override
    protected int getContentViewLayout() {
        return R.layout.activity_order_preview;
    }

    @Override
    protected void initView() {
        new ToolbarWrapper(this).setCustomTitle(R.string.order_title_preview);
        mAlertWrapper = new AlertWrapper();
        mProgressWrapper = new ProgressWrapper();
        mProgressWrapper.showProgress(this);
        enqueue(new ApiOrderPreview());
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        mProgressWrapper.dismissProgress();
        if (!success) return;
        switch (apiPath) {
            case ApiPath.ORDER_PREVIEW:
                ApiOrderPreview.Rsp previewRsp = (ApiOrderPreview.Rsp) rsp;
                mPaymentList = previewRsp.getData().getPaymentList();
                mShippingList = previewRsp.getData().getShippingList();
                showAddress(previewRsp.getData().getAddress());
                showGoods(previewRsp.getData().getGoodsList());
                String price = UserManager.getInstance().getCartBill().getGoodsPrice();
                tvGoodsPrice.setText(getString(R.string.order_goods_price, price));
                tvShippingPrice.setText(getString(R.string.order_shipping_price, ""));
                break;
            case ApiPath.ORDER_DONE:
                UserManager.getInstance().retrieveUserInfo();
                UserManager.getInstance().retrieveCartList();
                if (PAYMENT_CASH_ON_DELIVERY.equals(mPaymentCode)) {
                    finish();
                    return;
                }
                mAlertWrapper.setAlertText(R.string.order_msg_purchase_now)
                        .setConfirmListener(v -> finish())
                        .setCancelListener(v -> finish())
                        .showAlert(this);
                break;
            default:
                throw new UnsupportedOperationException(apiPath);
        }
    }

    @Override
    public void onEvent(UserEvent event) {
        super.onEvent(event);
        if (!UserManager.getInstance().hasUser()) {
            finish();
        }
    }

    @OnClick(R.id.layout_consignee)
    void navigateToManageAddress(View view) {
        Intent manage = new Intent(this, ManageAddressActivity.class);
        startActivity(manage);
    }

    @OnClick(R.id.text_payment)
    void showSelectPayment() {
        if (mPaymentList == null) return;
        new AlertDialog.Builder(this)
                .setTitle(R.string.order_choose_payment)
                .setItems(paymentsToStrings(mPaymentList), (dialog, which) -> {
                    Payment payment = mPaymentList.get(which);
                    mPaymentId = payment.getId();
                    mPaymentCode = payment.getCode();
                    tvPayment.setText(getString(R.string.order_payment_name, payment.getName()));
                    checkSummitEnabled();
                })
                .show();
    }

    @OnClick(R.id.text_shipping)
    void showSelectShipping() {
        if (mShippingList == null) return;
        new AlertDialog.Builder(this)
                .setTitle(R.string.order_choose_shipping)
                .setItems(shippingsToStrings(mShippingList), (dialog, which) -> {
                    Shipping shipping = mShippingList.get(which);
                    mShippingId = shipping.getId();
                    tvShipping.setText(getString(R.string.order_shipping_name, shipping.getName()));
                    tvShippingPrice.setText(getString(R.string.order_shipping_price, shipping.getPrice()));
                    checkSummitEnabled();
                })
                .show();
    }

    @OnClick(R.id.button_summit)
    void summitOrder() {
        mProgressWrapper.showProgress(this);
        ApiOrderDone apiOrderDone = new ApiOrderDone(mPaymentId, mShippingId);
        enqueue(apiOrderDone);
    }

    private void showAddress(Address address) {
        tvConsignee.setText(getString(R.string.order_consignee_name, address.getConsignee()));
        tvAddress.setText(String.format("(%s)%s%s - %s",
                address.getProvinceName(),
                address.getCityName(),
                address.getDistrictName(),
                address.getAddress()));
    }

    private void showGoods(List<CartGoods> goodsList) {
        for (CartGoods goods : goodsList) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_order_goods, goodsLayout, false);
            GoodsItemHolder holder = new GoodsItemHolder(view);
            holder.bind(goods);
            goodsLayout.addView(view);
        }
    }

    private String[] paymentsToStrings(List<Payment> paymentList) {
        ArrayList<String> list = new ArrayList<>();
        for (Payment payment : paymentList) {
            list.add(payment.getName());
        }
        return list.toArray(new String[list.size()]);
    }

    private String[] shippingsToStrings(List<Shipping> shippingList) {
        ArrayList<String> list = new ArrayList<>();
        for (Shipping shipping : shippingList) {
            list.add(shipping.getName() + "  " + shipping.getPrice());
        }
        return list.toArray(new String[list.size()]);
    }

    private void checkSummitEnabled() {
        if (mPaymentId != 0 && mShippingId != 0) {
            btnSummit.setEnabled(true);
        } else {
            btnSummit.setEnabled(false);
        }
    }

    final class GoodsItemHolder {
        @BindView(R.id.text_goods_name)
        TextView tvName;
        @BindView(R.id.text_amount)
        TextView tvAmount;
        private CartGoods mCartGoods;

        GoodsItemHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @SuppressLint("StringFormatMatches")
        public void bind(CartGoods cartGoods) {
            mCartGoods = cartGoods;
            tvName.setText(mCartGoods.getGoodsName());
            int number = mCartGoods.getGoodsNumber();
            tvAmount.setText(getString(R.string.order_goods_amount, number));
        }

        @OnClick
        void onClick() {
            Intent intent = GoodsActivity.getStartIntent(OrderPreviewActivity.this, mCartGoods.getGoodsId());
            startActivity(intent);
        }
    }

}
