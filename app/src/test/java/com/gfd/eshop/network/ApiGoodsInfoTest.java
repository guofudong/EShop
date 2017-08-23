package com.gfd.eshop.network;


import com.gfd.eshop.network.api.ApiGoodsInfo;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApiGoodsInfoTest extends ApiTest {

    @Test public void getGoodsInfo() throws IOException {

        ApiGoodsInfo apiGoodsInfo = new ApiGoodsInfo(78);

        ApiGoodsInfo.Rsp rsp = client.execute(apiGoodsInfo);
        Assert.assertTrue(rsp.getStatus().isSucceed());
    }
}