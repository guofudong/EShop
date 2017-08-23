package com.gfd.eshop.network;


import com.gfd.eshop.network.api.ApiSearch;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApiSearchTest extends ApiTest {

    @Test public void searchGoods() throws IOException {

        ApiSearch apiSearch = new ApiSearch(null, null);
        ApiSearch.Rsp rsp = client.execute(apiSearch);
        Assert.assertTrue(rsp.getStatus().isSucceed());
    }
}
