package com.gfd.eshop.base.widgets;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.IntDef;

import com.gfd.eshop.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Random;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class ImageGrid extends ViewGroup {

    public static final int GRID_THREE = 3;
    public static final int GRID_FOUR = 4;
    public static final int GRID_FIVE = 5;

    @IntDef({GRID_THREE, GRID_FOUR, GRID_FIVE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GridMode {
    }

    @BindViews({R.id.image_one, R.id.image_two, R.id.image_three, R.id.image_four, R.id.image_five})
    ImageView[] imageViews;

    private int mGridMode = GRID_FIVE;

    public ImageGrid(Context context) {
        super(context);
        init(context);
    }

    public ImageGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImageGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int left = 0;
        int top = 0;
        int width = r - l;
        int height = b - t;

        int diff = width - height;

        switch (mGridMode) {
            case GRID_THREE:
                imageViews[0].layout(left, top, left + height, height);
                imageViews[1].layout(left + height, top, width, top + height / 2);
                imageViews[2].layout(left + height, top + height / 2, width, height);
                imageViews[3].layout(left, top, left, top);
                imageViews[4].layout(left, top, left, top);
                break;
            case GRID_FOUR:
                imageViews[0].layout(left, top, left + width / 4, height);
                imageViews[1].layout(left + width / 4, top, left + width / 4 * 2, height);
                imageViews[2].layout(left + width / 4 * 2, top, left + width / 4 * 3, height);
                imageViews[3].layout(left + width / 4 * 3, top, width, height);
                imageViews[4].layout(left, top, left, top);
                break;
            case GRID_FIVE:
                imageViews[0].layout(left, top, left + diff, height);
                imageViews[1].layout(left + diff, top, left + diff + height / 2, top + height / 2);
                imageViews[2].layout(left + diff + height / 2, top, width, top + height / 2);
                imageViews[3].layout(left + diff, top + height / 2, left + diff + height / 2, height);
                imageViews[4].layout(left + diff + height / 2, top + height / 2, width, height);
                break;
            default:
                throw new IllegalStateException("Illegal GridMode: " + mGridMode);
        }
    }

    public void setGridMode(@GridMode int gridMode) {
        this.mGridMode = gridMode;
        requestLayout();
    }

    public void shuffle(int position) {
        switch (position % 3) {
            case 0:
                setGridMode(GRID_THREE);
                return;
            case 1:
                setGridMode(GRID_FOUR);
                return;
            case 2:
                setGridMode(GRID_FIVE);
                return;
            default:
                throw new RuntimeException("This is never going to happen!");
        }
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_image_grid, this, true);
        ButterKnife.bind(this);
    }
}
