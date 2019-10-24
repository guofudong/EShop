package com.gfd.eshop.feature.home;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseFragment;
import com.gfd.eshop.base.glide.GlideUtils;
import com.gfd.eshop.base.widgets.banner.BannerAdapter;
import com.gfd.eshop.base.widgets.banner.BannerLayout;
import com.gfd.eshop.base.wrapper.PtrWrapper;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.feature.goods.GoodsActivity;
import com.gfd.eshop.network.api.ApiHomeBanner;
import com.gfd.eshop.network.api.ApiHomeCategory;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.Banner;
import com.gfd.eshop.network.entity.SimpleGoods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 首页面.
 */
public class HomeFragment extends BaseFragment {

    private static final int[] PROMOTE_COLORS = {R.color.purple, R.color.orange, R.color.pink, R.color.colorPrimary};

    private static final int[] PROMOTE_PLACE_HOLDER = {
            R.drawable.mask_round_purple, R.drawable.mask_round_orange,
            R.drawable.mask_round_pink, R.drawable.mask_round_yellow
    };

    private SimpleGoods simpleGoods;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @BindView(R.id.list_home_goods)
    ListView goodsListView;

    private HomeGoodsAdapter mGoodsAdapter; // 首页商品列表适配器.
    private BannerAdapter<Banner> mBannerAdapter; //轮播图适配器.
    private PtrWrapper mPtrWrapper;

    private boolean mBannerRefreshed = false;
    private boolean mCategoryRefreshed = false;

    private ImageView[] mIvPromotes = new ImageView[4];
    private TextView mTvPromoteGoods;

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        //设置标题
        new ToolbarWrapper(this).setCustomTitle(R.string.home_title);
        //商品列表
        mGoodsAdapter = new HomeGoodsAdapter();
        goodsListView.setAdapter(mGoodsAdapter);
        //List-Header-View
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.partial_home_header, goodsListView, false);
        //轮播图
        BannerLayout bannerLayout = view.findViewById(R.id.layout_banner);
        mBannerAdapter = new BannerAdapter<Banner>() {
            @Override
            protected void bind(ViewHolder holder, Banner data) {
                GlideUtils.loadBanner(data.getPicture(), holder.ivBannerItem);
            }
        };
        bannerLayout.setBannerAdapter(mBannerAdapter);
        //促销单品
        mIvPromotes[0] = view.findViewById(R.id.image_promote_one);
        mIvPromotes[1] = view.findViewById(R.id.image_promote_two);
        mIvPromotes[2] = view.findViewById(R.id.image_promote_three);
        mIvPromotes[3] = view.findViewById(R.id.image_promote_four);
        mTvPromoteGoods = view.findViewById(R.id.text_promote_goods);
        goodsListView.addHeaderView(view);
        goodsListView.setDividerHeight(0);
        //下拉刷新
        mPtrWrapper = new PtrWrapper(this) {
            @Override
            public void onRefresh() {
                mBannerRefreshed = false;
                mCategoryRefreshed = false;
                // 获取轮播图和促销商品数据.
                enqueue(new ApiHomeBanner());
                // 获取首页商品分类数据.
                enqueue(new ApiHomeCategory());
            }
        };
        mPtrWrapper.postRefresh(50);
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        switch (apiPath) {
            case ApiPath.HOME_DATA:
                mBannerRefreshed = true;
                if (success) {
                    ApiHomeBanner.Rsp bannerRsp = (ApiHomeBanner.Rsp) rsp;
                    mBannerAdapter.reset(bannerRsp.getData().getBanners());
                    setPromoteGoods(bannerRsp.getData().getGoodsList());
                }
                break;
            case ApiPath.HOME_CATEGORY:
                mCategoryRefreshed = true;
                if (success) {
                    ApiHomeCategory.Rsp categoryRsp = (ApiHomeCategory.Rsp) rsp;
                    mGoodsAdapter.reset(categoryRsp.getData());
                }
                break;
            default:
                throw new UnsupportedOperationException(apiPath);
        }

        if (mBannerRefreshed && mCategoryRefreshed) {
            // 两个接口的数据都返回了, 才停止下拉刷新.
            mPtrWrapper.stopRefresh();
        }
    }

    /**
     * 设置显示促销商品.
     * @param simpleGoodsList
     */
    private void setPromoteGoods(final List<SimpleGoods> simpleGoodsList) {
        mTvPromoteGoods.setVisibility(View.VISIBLE);
        for (int i = 0; i < mIvPromotes.length; i++) {
            mIvPromotes[i].setVisibility(View.VISIBLE);
            simpleGoods = null;
            if (i < simpleGoodsList.size()) {
                simpleGoods = simpleGoodsList.get(i);
            } else {
                simpleGoods = simpleGoodsList.get(i - 1);
            }
            GlideUtils.loadPromote(simpleGoods.getImg(), mIvPromotes[i], PROMOTE_PLACE_HOLDER[i], PROMOTE_COLORS[i]);
            mIvPromotes[i].setOnClickListener(v -> {
                Intent intent = GoodsActivity.getStartIntent(getContext(), simpleGoods.getId());
                getActivity().startActivity(intent);
            });
        }
    }

}
