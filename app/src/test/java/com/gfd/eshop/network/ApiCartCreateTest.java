package com.gfd.eshop.network;


import com.gfd.eshop.network.api.ApiCartCreate;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApiCartCreateTest extends ApiSignInTest {

    @Test public void addToCart() throws IOException {
        signIn();
        ApiCartCreate apiCartCreate = new ApiCartCreate(73, 1);
        ApiCartCreate.Rsp rsp = client.execute(apiCartCreate);
        Assert.assertTrue(rsp.getStatus().isSucceed());
    }
}
