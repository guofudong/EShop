package com.gfd.eshop.network;

import androidx.annotation.NonNull;

import com.gfd.eshop.network.api.ApiAddressList;
import com.gfd.eshop.network.api.ApiCartList;
import com.gfd.eshop.network.api.ApiUserInfo;
import com.gfd.eshop.network.core.IUserManager;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.core.UiCallback;
import com.gfd.eshop.network.entity.Address;
import com.gfd.eshop.network.entity.CartBill;
import com.gfd.eshop.network.entity.CartGoods;
import com.gfd.eshop.network.entity.Session;
import com.gfd.eshop.network.entity.User;
import com.gfd.eshop.network.event.AddressEvent;
import com.gfd.eshop.network.event.CartEvent;
import com.gfd.eshop.network.event.UserEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 用户信息管理
 */
public class UserManager implements IUserManager {

    private static IUserManager sInstance = new UserManager();

    private EShopClient mClient = EShopClient.getInstance();
    private EventBus mBus = EventBus.getDefault();
    private Session mSession;
    private User mUser;
    private List<CartGoods> mCartGoodsList;
    private CartBill mCartBill;
    private List<Address> mAddressList;

    public static IUserManager getInstance() {
        return sInstance;
    }

    /**
     * 设置用户信息
     * @param user
     * @param session
     */
    @Override public void setUser(@NonNull User user, @NonNull Session session) {
        mUser = user;
        mSession = session;
        mBus.postSticky(new UserEvent());
        retrieveCartList();
        retrieveAddressList();
    }

    /**
     * 恢复用户信息
     */
    @Override public void retrieveUserInfo() {
        ApiUserInfo apiUserInfo = new ApiUserInfo();
        UiCallback callback = new UiCallback() {
            @Override
            public void onBusinessResponse(boolean success, ResponseEntity responseEntity) {
                if (success) {
                    ApiUserInfo.Rsp userRsp = (ApiUserInfo.Rsp) responseEntity;
                    mUser = userRsp.getUser();
                }
                mBus.postSticky(new UserEvent());
            }
        };
        mClient.enqueue(apiUserInfo, callback, getClass().getSimpleName());
    }

    /**
     * 恢复购物车数据
     */
    @Override public void retrieveCartList() {
        ApiCartList apiCartList = new ApiCartList();
        UiCallback cb = new UiCallback() {
            @Override
            public void onBusinessResponse(boolean success, ResponseEntity rsp) {
                if (success) {
                    ApiCartList.Rsp listRsp = (ApiCartList.Rsp) rsp;
                    mCartGoodsList = listRsp.getData().getGoodsList();
                    mCartBill = listRsp.getData().getCartBill();
                }
                mBus.postSticky(new CartEvent());
            }
        };
        mClient.enqueue(apiCartList, cb, getClass().getSimpleName());
    }

    /**
     * 恢复收货地址数据
     */
    @Override public void retrieveAddressList() {
        ApiAddressList apiAddressList = new ApiAddressList();
        UiCallback uiCallback = new UiCallback() {
            @Override
            public void onBusinessResponse(boolean success, ResponseEntity responseEntity) {
                if (success) {
                    ApiAddressList.Rsp listRsp = (ApiAddressList.Rsp) responseEntity;
                    mAddressList = listRsp.getData();
                }
                mBus.postSticky(new AddressEvent());
            }
        };
        mClient.enqueue(apiAddressList, uiCallback, getClass().getSimpleName());
    }

    /**
     * 获取默认的收货地址
     * @return
     */
    @Override public Address getDefaultAddress() {
        if (hasAddress()) {
            for (Address address : mAddressList) {
                if (address.isDefault()) return address;
            }
        }
        return null;
    }

    /** 清空用户数据*/
    @Override public void clear() {
        mUser = null;
        mSession = null;
        mCartBill = null;
        mCartGoodsList = null;
        mClient.cancelByTag(getClass().getSimpleName());
        mBus.postSticky(new UserEvent());
        mBus.postSticky(new CartEvent());
        mBus.postSticky(new AddressEvent());
    }

    @Override public boolean hasUser() {
        return mSession != null && mUser != null;
    }

    @Override public boolean hasCart() {
        return mCartGoodsList != null && !mCartGoodsList.isEmpty();
    }

    @Override public boolean hasAddress() {
        return mAddressList != null && !mAddressList.isEmpty();
    }

    @Override public Session getSession() {
        return mSession;
    }

    @Override public User getUser() {
        return mUser;
    }

    @Override public List<CartGoods> getCartGoodsList() {
        return mCartGoodsList;
    }

    @Override public CartBill getCartBill() {
        return mCartBill;
    }

    @Override public List<Address> getAddressList() {
        return mAddressList;
    }

}
