package com.gfd.eshop.feature.goods;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.wrapper.BadgeWrapper;
import com.gfd.eshop.base.wrapper.ToastWrapper;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.feature.cart.CartActivity;
import com.gfd.eshop.feature.goods.comments.GoodsCommentsFragment;
import com.gfd.eshop.feature.goods.details.GoodsDetailsFragment;
import com.gfd.eshop.feature.goods.info.GoodsInfoFragment;
import com.gfd.eshop.feature.mine.SignInActivity;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.api.ApiCartCreate;
import com.gfd.eshop.network.api.ApiGoodsInfo;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.GoodsInfo;
import com.gfd.eshop.network.event.CartEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.OnClick;

/**
 * 商品页, 包含三个子页面: <br/>
 * <li>商品信息 {@link GoodsInfoFragment}
 * <li>商品详情 {@link GoodsDetailsFragment}
 * <li>商品评价 {@link GoodsCommentsFragment}.
 */
public class GoodsActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String EXTRA_GOODS_ID = "EXTRA_GOODS_ID";

    /**
     * @param context 上下文对象
     * @param goodsId 商品Id
     * @return 用于启动此Activity的Intent对象
     */
    public static Intent getStartIntent(Context context, int goodsId) {
        Intent intent = new Intent(context, GoodsActivity.class);
        intent.putExtra(EXTRA_GOODS_ID, goodsId);
        return intent;
    }

    @BindViews({R.id.text_tab_goods, R.id.text_tab_details, R.id.text_tab_comments})
    List<TextView> tvTabList;
    @BindView(R.id.pager_goods) ViewPager goodsPager;
    @BindView(R.id.button_show_cart) ImageButton btnCart;

    private BadgeWrapper mBadgeView;
    private GoodsInfo mGoodsInfo;
    private GoodsSpecPopupWindow mGoodsSpecPopupWindow;
    private boolean mBuy;

    @Override protected int getContentViewLayout() {
        return R.layout.activity_goods;
    }

    @Override protected void initView() {
        new ToolbarWrapper(this);
        goodsPager.addOnPageChangeListener(this);
        mBadgeView = new BadgeWrapper(btnCart);
        // 获取商品信息.
        int goodsId = getIntent().getIntExtra(EXTRA_GOODS_ID, 0);
        enqueue(new ApiGoodsInfo(goodsId));
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        switch (apiPath) {
            case ApiPath.GOODS:
                if (success) {
                    ApiGoodsInfo.Rsp goodsRsp = (ApiGoodsInfo.Rsp) rsp;
                    mGoodsInfo = goodsRsp.getData();
                    goodsPager.setAdapter(new GoodsPagerAdapter(getSupportFragmentManager(), mGoodsInfo));
                    chooseTab(0);
                }
                break;
            case ApiPath.CART_CREATE:
                mGoodsSpecPopupWindow.dismiss();
                UserManager.getInstance().retrieveCartList();
                if (mBuy) {
                    Intent intent = new Intent(this, CartActivity.class);
                    startActivity(intent);
                } else {
                    ToastWrapper.show(R.string.goods_msg_add_success);
                }
                break;
            default:
                throw new UnsupportedOperationException(apiPath);
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_goods, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            ToastWrapper.show(R.string.action_share);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CartEvent cartEvent) {
        if (UserManager.getInstance().hasCart()) {
            mBadgeView.showNumber(UserManager.getInstance().getCartGoodsList().size());
        } else {
            mBadgeView.hide();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override public void onPageSelected(int position) {
        chooseTab(position);
    }

    @Override public void onPageScrollStateChanged(int state) {
    }

    public void selectPage(int position) {
        goodsPager.setCurrentItem(position, false);
        chooseTab(position);
    }

    @OnClick({R.id.text_tab_goods, R.id.text_tab_details, R.id.text_tab_comments})
    void onClickTab(TextView textView) {
        int position = tvTabList.indexOf(textView);
        goodsPager.setCurrentItem(position, false);
        chooseTab(position);
    }

    @OnClick({R.id.button_show_cart, R.id.button_add_cart, R.id.button_buy})
    void onClick(View view) {
        if (!UserManager.getInstance().hasUser()) {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
            return;
        }
        switch (view.getId()) {
            case R.id.button_show_cart:
                Intent cart = new Intent(this, CartActivity.class);
                startActivity(cart);
                break;
            case R.id.button_add_cart:
            case R.id.button_buy:
                if (mGoodsInfo == null) return;

                if (mGoodsSpecPopupWindow == null) {
                    mGoodsSpecPopupWindow = new GoodsSpecPopupWindow(this, mGoodsInfo);
                }

                mBuy = view.getId() == R.id.button_buy;
                mGoodsSpecPopupWindow.show(number -> {
                    ApiCartCreate apiCartCreate = new ApiCartCreate(mGoodsInfo.getId(), number);
                    enqueue(apiCartCreate);
                });
                break;
            default:
                throw new UnsupportedOperationException("Unsupported View Id");
        }
    }

    /**
     * 选择Tab标签, 注意此方法只改变Tab标签的UI效果, 不会改变ViewPager的位置.
     * @param position
     */
    private void chooseTab(int position) {
        Resources res = getResources();
        for (int i = 0; i < tvTabList.size(); i++) {
            tvTabList.get(i).setSelected(i == position);
            float textSize = i == position ? res.getDimension(R.dimen.font_large) :
                    res.getDimension(R.dimen.font_normal);
            tvTabList.get(i).setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }
    }

}
