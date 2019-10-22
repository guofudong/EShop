package com.gfd.eshop.base.widgets.banner;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.viewpager.widget.ViewPager;

public class BannerIndicator extends LinearLayout {

    private static final int DEFAULT_INDICATOR_WIDTH = 5;
    private ViewPager mViewpager;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mAnimatorResId = me.relex.circleindicator.R.animator.scale_with_alpha;
    private int mAnimatorReverseResId = 0;
    private int mIndicatorBackgroundResId = me.relex.circleindicator.R.drawable.white_radius;
    private int mIndicatorUnselectedBackgroundResId =
            me.relex.circleindicator.R.drawable.white_radius;
    private Animator mAnimatorOut;
    private Animator mAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private Animator mImmediateAnimatorIn;

    private int mLastPosition = -1;

    public BannerIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public BannerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BannerIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, me.relex.circleindicator.R.styleable.CircleIndicator);
        mIndicatorWidth = typedArray.getDimensionPixelSize(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_width, -1);
        mIndicatorHeight = typedArray.getDimensionPixelSize(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_height, -1);
        mIndicatorMargin = typedArray.getDimensionPixelSize(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_margin, -1);

        mAnimatorResId = typedArray.getResourceId(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_animator,
                me.relex.circleindicator.R.animator.scale_with_alpha);
        mAnimatorReverseResId = typedArray.getResourceId(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_animator_reverse, 0);
        mIndicatorBackgroundResId = typedArray.getResourceId(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_drawable,
                me.relex.circleindicator.R.drawable.white_radius);
        mIndicatorUnselectedBackgroundResId = typedArray.getResourceId(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_drawable_unselected,
                mIndicatorBackgroundResId);

        int orientation = typedArray.getInt(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_orientation, -1);
        setOrientation(orientation == VERTICAL ? VERTICAL : HORIZONTAL);

        int gravity = typedArray.getInt(
                me.relex.circleindicator.R.styleable.CircleIndicator_ci_gravity, -1);
        setGravity(gravity >= 0 ? gravity : Gravity.CENTER);

        typedArray.recycle();
    }

    private void checkIndicatorConfig(Context context) {
        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight =
                (mIndicatorHeight < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin =
                (mIndicatorMargin < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;

        mAnimatorResId = (mAnimatorResId == 0) ?
                me.relex.circleindicator.R.animator.scale_with_alpha : mAnimatorResId;

        mAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut.setDuration(0);

        mAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn.setDuration(0);

        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ?
                me.relex.circleindicator.R.drawable.white_radius : mIndicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId =
                (mIndicatorUnselectedBackgroundResId == 0) ? mIndicatorBackgroundResId
                        : mIndicatorUnselectedBackgroundResId;
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, mAnimatorResId);
    }

    private Animator createAnimatorIn(Context context) {
        Animator animatorIn;
        if (mAnimatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            animatorIn.setInterpolator(new ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
        }
        return animatorIn;
    }

    public void setViewPager(ViewPager viewPager) {
        mViewpager = viewPager;
        if (mViewpager != null && mViewpager.getAdapter() != null) {
            mLastPosition = -1;
            createIndicators();
            mViewpager.removeOnPageChangeListener(mInternalPageChangeListener);
            mViewpager.addOnPageChangeListener(mInternalPageChangeListener);

            mInternalPageChangeListener.onPageSelected(getRealCurrentPosition());
        }
    }

    private final ViewPager.OnPageChangeListener mInternalPageChangeListener =
            new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override public void onPageSelected(int position) {

            if (mViewpager.getAdapter() == null || getRealCount() <= 0) {
                return;
            }

            if (mAnimatorIn.isRunning()) {
                mAnimatorIn.end();
                mAnimatorIn.cancel();
            }

            if (mAnimatorOut.isRunning()) {
                mAnimatorOut.end();
                mAnimatorOut.cancel();
            }

            View currentIndicator;
            if (mLastPosition >= 0
                    && (currentIndicator = getChildAt(getRealPosition(mLastPosition))) != null) {
                currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
                mAnimatorIn.setTarget(currentIndicator);
                mAnimatorIn.start();
            }

            View selectedIndicator = getChildAt(getRealPosition(position));
            if (selectedIndicator != null) {
                selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
                mAnimatorOut.setTarget(selectedIndicator);
                mAnimatorOut.start();
            }
            mLastPosition = position;
        }

        @Override public void onPageScrollStateChanged(int state) {
        }
    };

    public DataSetObserver getDataSetObserver() {
        return mInternalDataSetObserver;
    }

    private DataSetObserver mInternalDataSetObserver = new DataSetObserver() {
        @Override public void onChanged() {
            super.onChanged();
            if (mViewpager == null) {
                return;
            }

            int newCount = getRealCount();
            int currentCount = getChildCount();

            if (newCount == currentCount) {  // No change
                return;
            } else if (mLastPosition < mViewpager.getAdapter().getCount()) {
                mLastPosition = mViewpager.getCurrentItem();
            } else {
                mLastPosition = -1;
            }

            createIndicators();
        }
    };


    private void createIndicators() {
        removeAllViews();

        int count = getRealCount();
        if (count <= 0) {
            return;
        }
        int currentItem = getRealCurrentPosition();
        int orientation = getOrientation();

        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(orientation, mIndicatorBackgroundResId, mImmediateAnimatorOut);
            } else {
                addIndicator(orientation, mIndicatorUnselectedBackgroundResId,
                        mImmediateAnimatorIn);
            }
        }
    }

    private void addIndicator(int orientation, @DrawableRes int backgroundDrawableId,
                              Animator animator) {
        if (animator.isRunning()) {
            animator.end();
            animator.cancel();
        }

        View indicator = new View(getContext());
        indicator.setBackgroundResource(backgroundDrawableId);
        addView(indicator, mIndicatorWidth, mIndicatorHeight);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) indicator.getLayoutParams();

        if (orientation == HORIZONTAL) {
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
        } else {
            lp.topMargin = mIndicatorMargin;
            lp.bottomMargin = mIndicatorMargin;
        }

        indicator.setLayoutParams(lp);

        animator.setTarget(indicator);
        animator.start();
    }

    private class ReverseInterpolator implements Interpolator {
        @Override public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int getRealCount() {
        InfinitePagerAdapter infinitePagerAdapter = (InfinitePagerAdapter) mViewpager.getAdapter();
        return infinitePagerAdapter.getRealCount();
    }

    private int getRealCurrentPosition() {

        if (getRealCount() == 0) return -1;

        return mViewpager.getCurrentItem() % getRealCount();
    }

    private int getRealPosition(int position) {

        if (getRealCount() == 0) return -1;

        return position % getRealCount();
    }

}
