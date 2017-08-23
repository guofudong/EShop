package com.gfd.eshop.network;


import com.gfd.eshop.network.api.ApiOrderList;

import org.junit.Test;

import java.io.IOException;

public class ApiOrderListTest extends ApiSignInTest {

    @Test public void getOrderList() throws IOException {
        signIn();
        ApiOrderList apiOrderList = new ApiOrderList(ApiOrderList.ORDER_UNCONFIRMED, null);
        client.execute(apiOrderList);
    }
}
