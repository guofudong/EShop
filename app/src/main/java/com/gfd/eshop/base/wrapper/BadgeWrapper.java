package com.gfd.eshop.base.wrapper;


import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import com.gfd.eshop.R;
import com.readystatesoftware.viewbadger.BadgeView;

/**
 * View增加角标显示
 */
public class BadgeWrapper {

    private BadgeView mBadgeView;

    /**
     * 构建角标
     * @param view：要添加角标显示的View
     */
    public BadgeWrapper(View view) {
        Context context = view.getContext();
        mBadgeView = new BadgeView(view.getContext(), view);
        int textSize = context.getResources().getDimensionPixelSize(R.dimen.font_small_fixed);
        mBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    /**
     * 显示数量
     * @param number
     */
    public void showNumber(int number) {
        mBadgeView.setText(String.valueOf(number));
        if (number > 0) {
            mBadgeView.show();
        } else {
            mBadgeView.hide();
        }
    }

    /** 隐藏角标*/
    public void hide() {
        mBadgeView.hide();
    }

}
