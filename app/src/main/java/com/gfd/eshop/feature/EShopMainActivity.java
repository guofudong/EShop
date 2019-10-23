package com.gfd.eshop.feature;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.feature.cart.CartFragment;
import com.gfd.eshop.feature.category.CategoryFragment;
import com.gfd.eshop.feature.home.HomeFragment;
import com.gfd.eshop.feature.mine.MineFragment;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.event.CartEvent;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * <p> App主页面Activity, 用来管理四个Fragment, 依次是:
 * <ol>
 * <li/> 首页面: {@link HomeFragment}
 * <li/> 分类页面: {@link CategoryFragment}
 * <li/> 购物车页面: {@link CartFragment}
 * <li/> 我的页面: {@link MineFragment}
 * </ol>
 */
public class EShopMainActivity extends BaseActivity implements OnTabSelectListener {

    @BindView(R.id.bottom_bar) BottomBar bottomBar;

    private HomeFragment mHomeFragment;
    private CategoryFragment mCategoryFragment;
    private CartFragment mCartFragment;
    private MineFragment mMineFragment;

    // 当前正在显示的Fragment
    private Fragment mCurrentFragment;

    @Override protected int getContentViewLayout() {
        return R.layout.activity_eshop_main;
    }

    @Override protected void initView() {
        // "内存重启"时(例如修改手机字体大小), 恢复之前的Fragment.
        // 注意此方法只有在父类的onCreate(Bundle)调用之后才有效.
        retrieveFragments();
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        // 主页面只负责四个Fragment的切换, 没有业务逻辑的处理.
    }

    @Override public void onTabSelected(@IdRes int tabId) {
        switch (tabId) {
            case R.id.tab_home://首页
                if (mHomeFragment == null) mHomeFragment = HomeFragment.newInstance();
                switchFragment(mHomeFragment);
                break;
            case R.id.tab_category://分类
                if (mCategoryFragment == null) mCategoryFragment = CategoryFragment.newInstance();
                switchFragment(mCategoryFragment);
                break;
            case R.id.tab_cart://购物车
                if (mCartFragment == null) mCartFragment = CartFragment.newInstance();
                switchFragment(mCartFragment);
                break;
            case R.id.tab_mine://我的
                if (mMineFragment == null) mMineFragment = MineFragment.newInstance();
                switchFragment(mMineFragment);
                break;
            default:
                throw new UnsupportedOperationException("Illegal branch!");
        }
    }

    @Override public void onBackPressed() {
        if (mCurrentFragment != mHomeFragment) {
            // 如果不是在HomeFragment, 则按返回键回到HomeFragment
            bottomBar.selectTabWithId(R.id.tab_home);
            return;
        }
        // 将Activity所在的Task移到后台, 而不是finish此Activity
        moveTaskToBack(true);
    }

    /**
     * 首页4个Fragment切换, 使用hide和show, 而不是replace.
     * @param target 要显示的目标Fragment.
     */
    private void switchFragment(Fragment target) {
        if (mCurrentFragment == target) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            // 隐藏当前正在显示的Fragment
            transaction.hide(mCurrentFragment);
        }
        if (target.isAdded()) {
            // 如果目标Fragment已经添加过, 就显示它
            transaction.show(target);
        } else {
            // 否则直接添加该Fragment
            transaction.add(R.id.layout_container, target, target.getClass().getName());
        }
        transaction.commit();
        mCurrentFragment = target;
    }

    /** 找回FragmentManager中存储的Fragment*/
    private void retrieveFragments() {
        FragmentManager manager = getSupportFragmentManager();
        mHomeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mCategoryFragment = (CategoryFragment) manager.findFragmentByTag(CategoryFragment.class.getName());
        mCartFragment = (CartFragment) manager.findFragmentByTag(CartFragment.class.getName());
        mMineFragment = (MineFragment) manager.findFragmentByTag(MineFragment.class.getName());
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(CartEvent event) {
        // 如果购物车数据发生变更, 需要更新BottomBar的角标
        if (UserManager.getInstance().hasCart()) {
            int total = UserManager.getInstance().getCartBill().getGoodsCount();
            bottomBar.getTabAtPosition(2).setBadgeCount(total);
        } else {
            bottomBar.getTabAtPosition(2).removeBadge();
        }
    }

}
