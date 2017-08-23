package com.gfd.eshop.network;


import com.gfd.eshop.network.api.ApiHomeBanner;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApiHomeBannerTest extends ApiTest {

    @Test public void getBanners() throws IOException {

        ApiHomeBanner apiHomeBanner = new ApiHomeBanner();
        ApiHomeBanner.Rsp rsp = client.execute(apiHomeBanner);

        Assert.assertTrue(rsp.getStatus().isSucceed());
    }
}
