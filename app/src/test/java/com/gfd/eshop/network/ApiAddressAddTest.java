package com.gfd.eshop.network;


import com.gfd.eshop.network.api.ApiAddressAdd;
import com.gfd.eshop.network.entity.Address;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApiAddressAddTest extends ApiSignInTest {

    @Test public void addAddress() throws IOException {

        signIn();

        Address address = new Address();
        address.setConsignee("YCJ");
        address.setTel("123456789");
        address.setEmail("sfe@163.com");
        address.setCountry(1);
        address.setProvince(2);
        address.setCity(52);
        address.setAddress("中华路34号");
        address.setZipcode("10086");
        address.setIsDefault(true);

        ApiAddressAdd apiAddressAdd = new ApiAddressAdd(address);

        ApiAddressAdd.Rsp rsp = client.execute(apiAddressAdd);
        Assert.assertTrue(rsp.getStatus().isSucceed());
    }
}
