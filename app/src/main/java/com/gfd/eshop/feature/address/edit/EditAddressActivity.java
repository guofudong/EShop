package com.gfd.eshop.feature.address.edit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.wrapper.ToastWrapper;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.api.ApiAddressAdd;
import com.gfd.eshop.network.api.ApiAddressUpdate;
import com.gfd.eshop.network.api.ApiRegion;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.entity.Address;
import com.gfd.eshop.network.entity.Region;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 新增/编辑 地址界面.
 */
public class EditAddressActivity extends BaseActivity {

    private static final String EXTRA_ADDRESS = "EXTRA_ADDRESS";

    /**
     * @param address 非空时是编辑地址, 否则是创建新地址.
     */
    public static Intent getStartIntent(Context context, @Nullable Address address) {
        Intent intent = new Intent(context, EditAddressActivity.class);
        intent.putExtra(EXTRA_ADDRESS, new Gson().toJson(address));
        return intent;
    }

    @BindView(R.id.edit_consignee) EditText etConsignee;
    @BindView(R.id.edit_tel) EditText etTel;
    @BindView(R.id.edit_detail) EditText etDetail;
    @BindView(R.id.edit_zipcode) EditText etZipcode;
    @BindView(R.id.edit_email) EditText etEmail;
    @BindView(R.id.button_save) Button btnSave;
    @BindView(R.id.text_region) TextView tvRegion;

    private String mConsignee; // 收件人姓名
    private String mTel; // 手机号码
    private String mDetail; // 详细地址
    private String mZipCode; // 邮编
    private String mEmail; // 邮箱

    private int mProvinceId; // 省份Id
    private String mProvinceName; // 省份名称

    private int mCityId; // 城市Id
    private String mCityName; // 城市名称

    private int mDistrictId; // 地区Id
    private String mDistrictName; // 地区名称

    private List<Region> mProvinceList; // 省份列表
    private List<Region> mCityList; // 城市列表
    private List<Region> mDistrictList; // 地区列表

    private Address mAddress;

    @Override protected int getContentViewLayout() {
        return R.layout.activity_edit_adress;
    }

    @Override protected void initView() {
        String str = getIntent().getStringExtra(EXTRA_ADDRESS);
        if (!TextUtils.isEmpty(str)) {
            mAddress = new Gson().fromJson(str, Address.class);
        }
        new ToolbarWrapper(this).setCustomTitle(mAddress == null ? R.string.address_title_add : R.string.address_title_edit);
        if (mAddress != null) {
            //设置收货人信息
            etConsignee.setText(mAddress.getConsignee());
            etDetail.setText(mAddress.getAddress());
            etTel.setText(mAddress.getTel());
            etZipcode.setText(mAddress.getZipcode());
            etEmail.setText(mAddress.getEmail());
            //设置地址信息
            mProvinceId = mAddress.getProvince();
            mProvinceName = mAddress.getProvinceName();
            mCityId = mAddress.getCity();
            mCityName = mAddress.getCityName();
            mDistrictId = mAddress.getDistrict();
            mDistrictName = mAddress.getDistrictName();
            setRegionText();
            checkAddressComplete();
        }
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        switch (apiPath) {
            case ApiPath.ADDRESS_ADD:
                UserManager.getInstance().retrieveAddressList();
                if (success) {
                    ToastWrapper.show(R.string.address_msg_add_success);
                    finish();
                }
                break;
            case ApiPath.REGION:
                if (success) {
                    ApiRegion.Rsp regionRsp = (ApiRegion.Rsp) rsp;
                    handleRegionResult(regionRsp.getData().getRegions());
                } else {
                    clearRegionInfo();
                }
                break;
            case ApiPath.ADDRESS_UPDATE:
                UserManager.getInstance().retrieveAddressList();
                if (success) {
                    ToastWrapper.show(R.string.address_msg_edit_success);
                    finish();
                }
                break;
            default:
                throw new UnsupportedOperationException(apiPath);
        }
    }

    @OnTextChanged({R.id.edit_consignee, R.id.edit_tel, R.id.edit_detail, R.id.edit_zipcode, R.id.edit_email})
    void onTextChanged() {
        mConsignee = etConsignee.getText().toString();
        mTel = etTel.getText().toString();
        mDetail = etDetail.getText().toString();
        mZipCode = etZipcode.getText().toString();
        mEmail = etEmail.getText().toString();
        checkAddressComplete();
    }

    @OnClick(R.id.text_region) void selectRegion() {
        clearRegionInfo();
        // 获取省份列表.
        enqueue(new ApiRegion(ApiRegion.ID_CHINA));
    }

    @OnClick(R.id.button_save) void saveAddress() {
        Address address;
        if (mAddress == null) {
            address = new Address();
        } else {
            address = mAddress;
        }
        address.setConsignee(mConsignee);
        address.setTel(mTel);
        address.setMobile(mTel);
        address.setCountry(ApiRegion.ID_CHINA);
        address.setProvince(mProvinceId);
        address.setCity(mCityId);
        address.setDistrict(mDistrictId);
        address.setAddress(mDetail);
        address.setZipcode(mZipCode);
        address.setEmail(mEmail);
        address.setIsDefault(true);
        if (mAddress == null) {
            // 添加新地址.
            ApiAddressAdd apiAddressAdd = new ApiAddressAdd(address);
            enqueue(apiAddressAdd);
        } else {
            ApiAddressUpdate apiAddressUpdate = new ApiAddressUpdate(address, address.getId());
            enqueue(apiAddressUpdate);
        }
    }

    private void checkAddressComplete() {
        if (TextUtils.isEmpty(mConsignee) || TextUtils.isEmpty(mTel) || TextUtils.isEmpty(mDetail)
                || TextUtils.isEmpty(mZipCode) || TextUtils.isEmpty(mEmail)) {
            btnSave.setEnabled(false);
            return;
        }
        if (mProvinceId == 0 || mCityId == 0 || mDistrictId == 0) {
            btnSave.setEnabled(false);
            return;
        }
        btnSave.setEnabled(true);
    }

    private void showSelectProvince() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.address_choose_province)
                .setItems(regionsToStrings(mProvinceList), (dialog, which) -> {
                    Region region = mProvinceList.get(which);
                    mProvinceId = region.getId();
                    mProvinceName = region.getName();
                    // 获取城市列表.
                    enqueue(new ApiRegion(mProvinceId));
                }).show();
    }

    private void showSelectCity() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.address_choose_city)
                .setItems(regionsToStrings(mCityList), (dialog, which) -> {
                    Region region = mCityList.get(which);
                    mCityId = region.getId();
                    mCityName = region.getName();
                    // 获取区域列表
                    enqueue(new ApiRegion(mCityId));
                }).show();
    }

    private void showSelectDistrict() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.address_choose_district)
                .setItems(regionsToStrings(mDistrictList), (dialog, which) -> {
                    Region region = mDistrictList.get(which);
                    mDistrictId = region.getId();
                    mDistrictName = region.getName();
                    setRegionText();
                    checkAddressComplete();
                }).show();
    }

    private String[] regionsToStrings(List<Region> regionList) {
        ArrayList<String> list = new ArrayList<>();
        for (Region region : regionList) {
            list.add(region.getName());
        }
        return list.toArray(new String[list.size()]);
    }

    private void clearRegionInfo() {
        mProvinceId = 0;
        mCityId = 0;
        mDistrictId = 0;
        mProvinceName = null;
        mCityName = null;
        mDistrictName = null;
        mProvinceList = null;
        mCityList = null;
        mDistrictList = null;
        tvRegion.setText(null);
        btnSave.setEnabled(false);
    }

    private void handleRegionResult(List<Region> data) {
        if (mProvinceList == null) {
            mProvinceList = data;
            showSelectProvince();
        } else if (mCityList == null) {
            mCityList = data;
            showSelectCity();
        } else {
            mDistrictList = data;
            showSelectDistrict();
        }
    }

    private void setRegionText() {
        String info = String.format("%s - %s%s", mProvinceName, mCityName, mDistrictName);
        tvRegion.setText(info);
    }

}
