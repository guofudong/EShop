package com.gfd.eshop.feature.mine;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseFragment;
import com.gfd.eshop.base.wrapper.BadgeWrapper;
import com.gfd.eshop.feature.address.manage.ManageAddressActivity;
import com.gfd.eshop.feature.collect.CollectActivity;
import com.gfd.eshop.feature.help.HelpActivity;
import com.gfd.eshop.feature.order.list.OrderListActivity;
import com.gfd.eshop.feature.settings.SettingsActivity;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.api.ApiOrderList;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.User;
import com.gfd.eshop.network.event.UserEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的页面.
 */
public class MineFragment extends BaseFragment {

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @BindView(R.id.text_username)
    TextView tvName;

    @BindView(R.id.text_wait_pay)
    TextView tvWaitPay;
    @BindView(R.id.text_wait_ship)
    TextView tvWaitShip;
    @BindView(R.id.text_shipped)
    TextView tvShipped;
    @BindView(R.id.text_history)
    TextView tvHistory;

    private BadgeWrapper mWaitPayBadge;
    private BadgeWrapper mWaitShipBadge;
    private BadgeWrapper mShippedBadge;

    @Override
    protected int getContentViewLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mWaitPayBadge = new BadgeWrapper(tvWaitPay);
        mWaitShipBadge = new BadgeWrapper(tvWaitShip);
        mShippedBadge = new BadgeWrapper(tvShipped);
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
    }

    @Override
    public void onEvent(UserEvent event) {
        super.onEvent(event);
        if (UserManager.getInstance().hasUser()) {
            User user = UserManager.getInstance().getUser();
            tvName.setText(user.getName());
            mWaitPayBadge.showNumber(user.getOrderNum().getAwaitPay());
            mWaitShipBadge.showNumber(user.getOrderNum().getAwaitShip());
            mShippedBadge.showNumber(user.getOrderNum().getShipped());
        } else {
            tvName.setText(R.string.mine_sign_in_or_sign_up);
            mWaitPayBadge.hide();
            mWaitShipBadge.hide();
            mShippedBadge.hide();
        }
    }

    @OnClick({R.id.text_username, R.id.text_manage_address, R.id.text_wait_pay, R.id.text_order_unconfirmed,
            R.id.text_wait_ship, R.id.text_shipped, R.id.text_history, R.id.text_favorite,})
    void onClick(View view) {
        if (!UserManager.getInstance().hasUser()) {
            Intent intent = new Intent(getContext(), SignInActivity.class);
            getActivity().startActivity(intent);
            return;
        }
        switch (view.getId()) {
            case R.id.text_username:
                break;
            case R.id.text_manage_address:
                Intent manageAddress = new Intent(getContext(), ManageAddressActivity.class);
                startActivity(manageAddress);
                break;
            case R.id.text_wait_pay:
                Intent waitPay = OrderListActivity
                        .getStartIntent(getContext(), ApiOrderList.ORDER_AWAIT_PAY);
                startActivity(waitPay);
                break;
            case R.id.text_order_unconfirmed:
                Intent unconfirmed = OrderListActivity
                        .getStartIntent(getContext(), ApiOrderList.ORDER_UNCONFIRMED);
                startActivity(unconfirmed);
                break;
            case R.id.text_wait_ship:
                Intent waitShip = OrderListActivity
                        .getStartIntent(getContext(), ApiOrderList.ORDER_AWAIT_SHIP);
                startActivity(waitShip);
                break;
            case R.id.text_shipped:
                Intent shipped = OrderListActivity
                        .getStartIntent(getContext(), ApiOrderList.ORDER_SHIPPED);
                startActivity(shipped);
                break;
            case R.id.text_history:
                Intent history = OrderListActivity
                        .getStartIntent(getContext(), ApiOrderList.ORDER_FINISHED);
                startActivity(history);
                break;
            case R.id.text_favorite:
                Intent collect = new Intent(getContext(), CollectActivity.class);
                startActivity(collect);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    @OnClick(R.id.button_setting)
    void navigateToSettings() {
        Intent intent = new Intent(getContext(), SettingsActivity.class);
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.text_help)
    void navigateToHelp() {
        Intent intent = new Intent(getContext(), HelpActivity.class);
        getActivity().startActivity(intent);
    }

}
