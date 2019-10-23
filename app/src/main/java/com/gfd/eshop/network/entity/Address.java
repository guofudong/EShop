package com.gfd.eshop.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 收件人地址.
 */
public class Address {

    @SerializedName("id") private int mId;

    @SerializedName("consignee") private String mConsignee;

    @SerializedName("email") private String mEmail;

    @SerializedName("country") private int mCountry;

    @SerializedName("country_name") private String mCountryName;

    @SerializedName("province") private int mProvince;

    @SerializedName("province_name") private String mProvinceName;

    @SerializedName("city") private int mCity;

    @SerializedName("city_name") private String mCityName;

    @SerializedName("district") private int mDistrict;

    @SerializedName("district_name") private String mDistrictName;

    @SerializedName("address") private String mAddress;

    @SerializedName("zipcode") private String mZipcode;

    @SerializedName("tel") private String mTel;

    @SerializedName("mobile") private String mMobile;

    @SerializedName("default_address") private int mDefault;

    public int getId() {
        return mId;
    }

    public String getConsignee() {
        return mConsignee;
    }

    public String getEmail() {
        return mEmail;
    }

    public int getCountry() {
        return mCountry;
    }

    public int getCity() {
        return mCity;
    }

    public int getDistrict() {
        return mDistrict;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getZipcode() {
        return mZipcode;
    }

    public String getTel() {
        return mTel;
    }

    public boolean isDefault() {
        return mDefault == 1;
    }

    public void setConsignee(String consignee) {
        mConsignee = consignee;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public void setCountry(int country) {
        mCountry = country;
    }

    public void setCity(int city) {
        mCity = city;
    }

    public void setDistrict(int district) {
        mDistrict = district;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setZipcode(String zipcode) {
        mZipcode = zipcode;
    }

    public void setTel(String tel) {
        mTel = tel;
    }

    public void setIsDefault(boolean isDefault) {
        mDefault = isDefault ? 1 : 0;
    }

    public int getProvince() {
        return mProvince;
    }

    public void setProvince(int province) {
        mProvince = province;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public void setCountryName(String countryName) {
        mCountryName = countryName;
    }

    public String getProvinceName() {
        return mProvinceName;
    }

    public void setProvinceName(String provinceName) {
        mProvinceName = provinceName;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getDistrictName() {
        return mDistrictName;
    }

    public void setDistrictName(String districtName) {
        mDistrictName = districtName;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

}
