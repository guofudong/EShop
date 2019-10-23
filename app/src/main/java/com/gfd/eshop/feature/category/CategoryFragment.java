package com.gfd.eshop.feature.category;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseFragment;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.feature.search.SearchGoodsActivity;
import com.gfd.eshop.network.api.ApiCategory;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.CategoryPrimary;
import com.gfd.eshop.network.entity.Filter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;

/**
 * 分类页面.
 */
public class CategoryFragment extends BaseFragment {

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @BindView(R.id.list_category)
    ListView categoryListView;
    @BindView(R.id.list_children)
    ListView childrenListView;

    private CategoryAdapter mCategoryAdapter;
    private ChildrenAdapter mChildrenAdapter;
    private List<CategoryPrimary> mData;

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_category;
    }

    @Override
    protected void initView() {
        //设置标题
        new ToolbarWrapper(this)
                .setShowBack(false)
                .setShowTitle(false)
                .setCustomTitle(R.string.category_title);
        //左侧分类list
        mCategoryAdapter = new CategoryAdapter();
        categoryListView.setAdapter(mCategoryAdapter);
        //右侧分类下的二级分类
        mChildrenAdapter = new ChildrenAdapter();
        childrenListView.setAdapter(mChildrenAdapter);
        getData();
    }

    @OnItemClick(R.id.list_category)
    void onItemClick(int position) {
        chooseCategory(position);
    }

    @OnItemClick(R.id.list_children)
    void onChildrenItemClick(int position) {
        int categoryId = mChildrenAdapter.getItem(position).getId();
        navigateToSearch(categoryId);
    }

    /**
     * 获取分类数据
     */
    private void getData() {
        if (mData != null) {
            updateCategory();
        } else {
            enqueue(new ApiCategory());
        }
    }

    /**
     * 更新分类数据
     */
    private void updateCategory() {
        mCategoryAdapter.reset(mData);
        chooseCategory(0);
    }

    /**
     * 选择分类
     *
     * @param position
     */
    private void chooseCategory(int position) {
        categoryListView.setItemChecked(position, true);
        mChildrenAdapter.reset(mCategoryAdapter.getItem(position).getChildren());
    }

    /**
     * 搜索
     * @param categoryId
     */
    private void navigateToSearch(int categoryId) {
        Filter filter = new Filter();
        filter.setCategoryId(categoryId);
        Intent intent = SearchGoodsActivity.getStartIntent(getContext(), filter);
        getActivity().startActivity(intent);
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        if (!ApiPath.CATEGORY.equals(apiPath)) {
            throw new UnsupportedOperationException(apiPath);
        }
        if (success) {
            ApiCategory.Rsp categoryRsp = (ApiCategory.Rsp) rsp;
            mData = categoryRsp.getData();
            updateCategory();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_search) {
            int position = categoryListView.getCheckedItemPosition();
            int categoryId = mCategoryAdapter.getItem(position).getId();
            navigateToSearch(categoryId);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // 请求失败的简化处理方案: 界面切换时触发重新请求.
        if (!hidden && mData == null) {
            enqueue(new ApiCategory());
        }
    }

}
