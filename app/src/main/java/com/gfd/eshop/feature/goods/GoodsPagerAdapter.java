package com.gfd.eshop.feature.goods;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gfd.eshop.feature.goods.comments.GoodsCommentsFragment;
import com.gfd.eshop.feature.goods.details.GoodsDetailsFragment;
import com.gfd.eshop.feature.goods.info.GoodsInfoFragment;
import com.gfd.eshop.network.entity.GoodsInfo;

/**
 * 用于商品页{@link GoodsActivity}中, 三个子页面的管理.
 */
public class GoodsPagerAdapter extends FragmentPagerAdapter {

    private GoodsInfo mGoodsInfo;

    GoodsPagerAdapter(FragmentManager fm, GoodsInfo goodsInfo) {
        super(fm);
        this.mGoodsInfo = goodsInfo;
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return GoodsInfoFragment.newInstance(mGoodsInfo);
            case 1:
                return GoodsDetailsFragment.newInstance(mGoodsInfo);
            case 2:
                return GoodsCommentsFragment.newInstance();
            default:
                throw new UnsupportedOperationException("Illegal Position: " + position);
        }
    }

    @Override public int getCount() {
        return 3;
    }

}
