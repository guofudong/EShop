package com.gfd.eshop.base.widgets.banner;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.ViewPager;

import com.gfd.eshop.R;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerLayout extends RelativeLayout {

    private static final long sDuration = 4000;

    @BindView(R.id.pager_banner)
    ViewPager pagerBanner;
    @BindView(R.id.indicator) BannerIndicator mBannerIndicator;

    private BannerAdapter mBannerAdapter;
    private CyclingHandler mCyclingHandler;
    private Timer mCycleTimer;
    private TimerTask mCycleTask;

    private long mResumeCyclingTime = 0;


    private static class CyclingHandler extends Handler {
        private WeakReference<BannerLayout> mBannerReference;

        public CyclingHandler(BannerLayout bannerLayout) {
            mBannerReference = new WeakReference<>(bannerLayout);
        }

        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mBannerReference == null) return;
            BannerLayout bannerLayout = mBannerReference.get();
            if (bannerLayout == null) return;

            if (System.currentTimeMillis() < bannerLayout.mResumeCyclingTime) {
                return;
            }

            bannerLayout.moveToNextPosition();
        }
    }

    public BannerLayout(Context context) {
        super(context);
        init(context);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mCycleTimer = new Timer();
        mCycleTask = new TimerTask() {
            @Override public void run() {
                mCyclingHandler.sendEmptyMessage(0);
            }
        };

        mCycleTimer.schedule(mCycleTask, sDuration, sDuration);
    }

    @Override protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mCycleTimer.cancel();
        mCycleTask.cancel();
        mCycleTimer = null;
        mCycleTask = null;
    }


    @Override public boolean dispatchTouchEvent(MotionEvent ev) {
        mResumeCyclingTime = System.currentTimeMillis() + sDuration;
        return super.dispatchTouchEvent(ev);
    }

    public void setBannerAdapter(BannerAdapter bannerAdapter) {
        this.mBannerAdapter = bannerAdapter;
        InfinitePagerAdapter infiniteAdapter = new InfinitePagerAdapter(bannerAdapter, pagerBanner);
        pagerBanner.setAdapter(infiniteAdapter);
        mBannerIndicator.setViewPager(pagerBanner);
        infiniteAdapter.registerDataSetObserver(mBannerIndicator.getDataSetObserver());
    }

    public BannerAdapter getBannerAdapter() {
        return mBannerAdapter;
    }

    public void moveToNextPosition() {

        if (getBannerAdapter() == null) {
            throw new IllegalStateException("You did not set a banner adapter!");
        }

        int count = pagerBanner.getAdapter().getCount();
        if (count == 0) return;

        if (pagerBanner.getCurrentItem() == count - 1) {
            pagerBanner.setCurrentItem(0, true);
        } else {
            pagerBanner.setCurrentItem(pagerBanner.getCurrentItem() + 1, true);
        }

    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_banner_layout, this, true);
        ButterKnife.bind(this);
        mCyclingHandler = new CyclingHandler(this);
    }
}
