package com.gfd.eshop.network;


import com.gfd.eshop.network.api.ApiCartDelete;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApiCartDeleteTest extends ApiCartCreateTest {

    @Test public void deleteFromCart() throws IOException {

        addToCart();
        ApiCartDelete apiCartDelete = new ApiCartDelete(73);
        ApiCartDelete.Rsp rsp = client.execute(apiCartDelete);
        Assert.assertTrue(rsp.getStatus().isSucceed());
    }
}
