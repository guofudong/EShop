package com.gfd.eshop.base.wrapper;


import android.content.Context;
import android.util.TypedValue;
import android.view.View;

import com.gfd.eshop.R;
import com.readystatesoftware.viewbadger.BadgeView;

public class BadgeWrapper {

    private BadgeView mBadgeView;

    public BadgeWrapper(View view) {
        Context context = view.getContext();
        mBadgeView = new BadgeView(view.getContext(), view);
        int textSize = context.getResources().getDimensionPixelSize(R.dimen.font_small_fixed);
        mBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    public void showNumber(int number) {
        mBadgeView.setText(String.valueOf(number));
        if (number > 0) {
            mBadgeView.show();
        } else {
            mBadgeView.hide();
        }
    }

    public void hide() {
        mBadgeView.hide();
    }
}
