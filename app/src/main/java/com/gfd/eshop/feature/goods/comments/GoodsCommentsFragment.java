package com.gfd.eshop.feature.goods.comments;


import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseFragment;
import com.gfd.eshop.network.core.ResponseEntity;

/**
 * 商品评价页面: 不实现, 用空白页面.
 */
public class GoodsCommentsFragment extends BaseFragment {

    public static GoodsCommentsFragment newInstance() {
        return new GoodsCommentsFragment();
    }

    @Override protected int getContentViewLayout() {
        return R.layout.fragment_goods_comments;
    }

    @Override protected void initView() {
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
    }
    
}
