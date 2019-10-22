package com.gfd.eshop.base.widgets.banner;


import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class InfinitePagerAdapter extends PagerAdapter {

    private static final int MAX_SIZE = 10000;

    private BannerAdapter mRealAdapter;

    public InfinitePagerAdapter(BannerAdapter realAdapter, final ViewPager viewPager) {
        this.mRealAdapter = realAdapter;

        mRealAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override public void onChanged() {
                super.onChanged();
                InfinitePagerAdapter.this.notifyDataSetChanged();

                if (getRealCount() != 0) {
                    int item = MAX_SIZE / 2 / getRealCount() * getRealCount();
                    viewPager.setCurrentItem(item, false);
                }
            }

            @Override public void onInvalidated() {
                super.onInvalidated();
            }
        });
    }

    @Override public int getCount() {
        return getRealCount() == 0 ? 0 : MAX_SIZE;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {

        if (getRealCount() == 0) {
            return null;
        }

        int realPosition = position % getRealCount();

        return mRealAdapter.instantiateItem(container, realPosition);
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {

        if (getRealCount() == 0) {
            return;
        }
        int realPosition = position % getRealCount();

        mRealAdapter.destroyItem(container, realPosition, object);
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return mRealAdapter.isViewFromObject(view, object);
    }

    @Override public void finishUpdate(ViewGroup container) {
        mRealAdapter.finishUpdate(container);
    }

    @Override public void restoreState(Parcelable state, ClassLoader loader) {
        mRealAdapter.restoreState(state, loader);
    }

    @Override public Parcelable saveState() {
        return mRealAdapter.saveState();
    }

    @Override public void startUpdate(ViewGroup container) {
        mRealAdapter.startUpdate(container);
    }

    @Override public int getItemPosition(Object object) {
        return mRealAdapter.getItemPosition(object);
    }

    public int getRealCount() {
        return mRealAdapter.getCount();
    }
}
