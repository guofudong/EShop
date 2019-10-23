package com.gfd.eshop.feature.cart;



import androidx.fragment.app.FragmentTransaction;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.network.core.ResponseEntity;

/**
 * 购物车页面
 */
public class CartActivity extends BaseActivity {

    private static final String CART_FRAGMENT_TAG = CartFragment.class.getSimpleName();

    @Override protected int getContentViewLayout() {
        return R.layout.activity_cart;
    }

    @Override protected void initView() {
        if (getSupportFragmentManager().findFragmentByTag(CART_FRAGMENT_TAG) == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container, CartFragment.newInstance(), CART_FRAGMENT_TAG);
            transaction.commit();
        }
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
    }

}
