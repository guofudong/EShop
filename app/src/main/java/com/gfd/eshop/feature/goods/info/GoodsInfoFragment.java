package com.gfd.eshop.feature.goods.info;


import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseFragment;
import com.gfd.eshop.base.wrapper.PhotoWrapper;
import com.gfd.eshop.base.wrapper.ProgressWrapper;
import com.gfd.eshop.base.wrapper.ToastWrapper;
import com.gfd.eshop.feature.goods.GoodsActivity;
import com.gfd.eshop.feature.mine.SignInActivity;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.api.ApiCollectCreate;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.GoodsInfo;
import com.gfd.eshop.network.entity.Picture;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

/**
 * 商品信息Fragment. 此Fragment不可直接实例化, 需使用{@link #newInstance(GoodsInfo)}方法创建新对象.
 * <p/>
 * 此Fragment主要显示{@link GoodsInfo}实体中的信息.
 */
public class GoodsInfoFragment extends BaseFragment {

    private static final String ARGUMENT_GOODS_INFO = "ARGUMENT_GOODS_INFO";

    /**
     * @param goodsInfo 待显示的商品信息实体
     * @return 新的GoodsInfoFragment对象
     */
    public static GoodsInfoFragment newInstance(GoodsInfo goodsInfo) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_GOODS_INFO, new Gson().toJson(goodsInfo));

        GoodsInfoFragment fragment = new GoodsInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.pager_goods_pictures)
    ViewPager picturesPager; // 用于显示商品图片
    @BindView(R.id.indicator) CircleIndicator circleIndicator; // ViewPager的圆点指示器
    @BindView(R.id.text_goods_name) TextView tvGoodsName; // 商品名称
    @BindView(R.id.text_goods_price) TextView tvGoodsPrice; // 商品价格
    @BindView(R.id.text_market_price) TextView tvMarketPrice; // 商场价格
    @BindView(R.id.button_favorite) ImageButton btnFavorite; // 收藏

    private GoodsInfo mGoodsInfo;
    private ProgressWrapper mProgressWrapper;
    private PhotoWrapper mPhotoWrapper;

    @Override protected int getContentViewLayout() {
        return R.layout.fragment_goods_info;
    }

    @Override protected void initView() {
        mProgressWrapper = new ProgressWrapper();
        mPhotoWrapper = new PhotoWrapper();
        // 获取传入的商品信息实体
        String str = getArguments().getString(ARGUMENT_GOODS_INFO);
        mGoodsInfo = new Gson().fromJson(str, GoodsInfo.class);
        // 设置显示商品图片的ViewPager
        GoodsPictureAdapter adapter = new GoodsPictureAdapter(mGoodsInfo.getPictures()) {
            @Override public void onImageClicked(Picture picture) {
                mPhotoWrapper.showPhoto(GoodsInfoFragment.this, picture.getLarge());
            }
        };
        picturesPager.setAdapter(adapter);
        circleIndicator.setViewPager(picturesPager);
        // 设置商品名称, 价格等信息
        tvGoodsName.setText(mGoodsInfo.getName());
        tvGoodsPrice.setText(mGoodsInfo.getShopPrice());
        // 设置商场价格, 并添加删除线
        String marketPrice = mGoodsInfo.getMarketPrice();
        SpannableString spannableString = new SpannableString(marketPrice);
        spannableString.setSpan(new StrikethroughSpan(), 0, marketPrice.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvMarketPrice.setText(spannableString);
        btnFavorite.setSelected(mGoodsInfo.isCollected());
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        if (!ApiPath.COLLECT_CREATE.equals(apiPath)) {
            throw new UnsupportedOperationException(apiPath);
        }
        mProgressWrapper.dismissProgress();
        if (success) {
            btnFavorite.setSelected(true);
        }
    }

    @OnClick(R.id.text_goods_comments) void changeToComments() {
        // 切换到商品评价Fragment
        GoodsActivity activity = (GoodsActivity) getActivity();
        activity.selectPage(2);
    }

    @OnClick(R.id.text_goods_details) void changeToDetails() {
        // 切换到商品详情Fragment
        GoodsActivity activity = (GoodsActivity) getActivity();
        activity.selectPage(1);
    }

    @OnClick(R.id.button_favorite) void collectGoods() {
        // 收藏商品
        if (btnFavorite.isSelected()) {
            ToastWrapper.show(R.string.collect_msg_already_collected);
            return;
        }
        if (!UserManager.getInstance().hasUser()) {
            Intent intent = new Intent(getContext(), SignInActivity.class);
            startActivity(intent);
            return;
        }
        mProgressWrapper.showProgress(this);
        ApiCollectCreate apiCollectCreate = new ApiCollectCreate(mGoodsInfo.getId());
        enqueue(apiCollectCreate);
    }

}
