package com.gfd.eshop.feature.cart;


import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseFragment;
import com.gfd.eshop.base.wrapper.AlertWrapper;
import com.gfd.eshop.base.wrapper.ProgressWrapper;
import com.gfd.eshop.base.wrapper.PtrWrapper;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.feature.address.edit.EditAddressActivity;
import com.gfd.eshop.feature.goods.GoodsActivity;
import com.gfd.eshop.feature.mine.SignInActivity;
import com.gfd.eshop.feature.order.preview.OrderPreviewActivity;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.api.ApiCartDelete;
import com.gfd.eshop.network.api.ApiCartUpdate;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.CartGoods;
import com.gfd.eshop.network.event.CartEvent;
import com.gfd.eshop.network.event.UserEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/**
 * 购物车页面.
 */
public class CartFragment extends BaseFragment {

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @BindView(R.id.list_cart_goods) ListView cartListView; // 购物车商品列表.
    @BindView(R.id.text_total_price) TextView tvTotalPrice; // 商品总价.
    @BindView(R.id.layout_empty) ViewGroup emptyLayout; // 未登录时显示的空页面.
    @BindView(R.id.layout_empty_cart) ViewGroup emptyCartLayout;

    private CartGoodsAdapter mGoodsAdapter; // 商品列表的适配器.
    private PtrWrapper mPtrWrapper; // 下拉刷新包装类.
    private AlertWrapper mAlertWrapper = new AlertWrapper(); // 对话框包装类.
    private ProgressWrapper mProgressWrapper = new ProgressWrapper(); // 加载进度条包装类.

    @Override protected int getContentViewLayout() {
        return R.layout.fragment_cart;
    }

    @Override protected void initView() {
        new ToolbarWrapper(this).setCustomTitle(R.string.cart_title);
        mGoodsAdapter = new CartGoodsAdapter() {
            @Override public void numberChanged(CartGoods goods, int number) {
                // 更新购物车中的商品数量.
                mProgressWrapper.showProgress(CartFragment.this);
                ApiCartUpdate apiCartUpdate = new ApiCartUpdate(goods.getRecId(), number);
                enqueue(apiCartUpdate);
            }
        };
        mGoodsAdapter.reset(UserManager.getInstance().getCartGoodsList());
        cartListView.setAdapter(mGoodsAdapter);
        mPtrWrapper = new PtrWrapper(this) {
            @Override public void onRefresh() {
                if (UserManager.getInstance().hasUser()) {
                    UserManager.getInstance().retrieveCartList();
                } else {
                    stopRefresh();
                }
            }
        };
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        switch (apiPath) {
            case ApiPath.CART_DELETE:
            case ApiPath.CART_UPDATE:
                if (success) {
                    UserManager.getInstance().retrieveCartList();
                } else {
                    mProgressWrapper.dismissProgress();
                }
                break;
            default:
                throw new UnsupportedOperationException(apiPath);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CartEvent event) {
        mPtrWrapper.stopRefresh();
        mProgressWrapper.dismissProgress();
        if (UserManager.getInstance().hasCart()) {
            mGoodsAdapter.reset(UserManager.getInstance().getCartGoodsList());
            tvTotalPrice.setText(UserManager.getInstance().getCartBill().getGoodsPrice());
            emptyCartLayout.setVisibility(View.INVISIBLE);
        } else {
            emptyCartLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override public void onEvent(UserEvent event) {
        super.onEvent(event);
        if (UserManager.getInstance().hasUser()) {
            emptyLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.button_signin) void navigateToSignIn() {
        // 跳转到注册页面.
        Intent intent = new Intent(getContext(), SignInActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.button_summit) void summit() {
        if (UserManager.getInstance().hasAddress()) {
            // 跳转到订单预览.
            Intent intent = new Intent(getContext(), OrderPreviewActivity.class);
            getActivity().startActivity(intent);
        } else {
            // 添加收件人地址.
            Intent intent = EditAddressActivity.getStartIntent(getContext(), null);
            getActivity().startActivity(intent);
        }
    }

    @OnItemLongClick(R.id.list_cart_goods) boolean onItemLongClick(int position) {
        // 弹出删除商品对话框.
        final CartGoods cartGoods = mGoodsAdapter.getItem(position);
        mAlertWrapper.setAlertText(R.string.cart_msg_delete_goods)
                .setConfirmListener(v -> {
                    mAlertWrapper.dismiss();
                    // 从购物车中删除商品
                    mProgressWrapper.showProgress(CartFragment.this);
                    ApiCartDelete apiCartDelete = new ApiCartDelete(cartGoods.getRecId());
                    enqueue(apiCartDelete);
                })
                .showAlert(this);
        return true;
    }

    @OnItemClick(R.id.list_cart_goods) void onItemClick(int position) {
        // 跳转到商品详情页面.
        CartGoods cartGoods = mGoodsAdapter.getItem(position);
        Intent intent = GoodsActivity.getStartIntent(getContext(), cartGoods.getGoodsId());
        startActivity(intent);
    }

}
