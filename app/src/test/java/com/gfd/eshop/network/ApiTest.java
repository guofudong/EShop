package com.gfd.eshop.network;


import org.junit.Before;

public class ApiTest {

    EShopClient client;

    @Before public void setUp() throws Exception {
        client = EShopClient.getInstance();
        client.setShowLog(true);
    }

}
