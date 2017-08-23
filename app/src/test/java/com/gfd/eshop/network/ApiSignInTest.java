package com.gfd.eshop.network;

import com.gfd.eshop.network.api.ApiSignIn;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApiSignInTest extends ApiTest {

    @Test public void signIn() throws IOException {

        ApiSignIn apiSignIn = new ApiSignIn("ycj", "123456");
        ApiSignIn.Rsp rsp = client.execute(apiSignIn);

        Assert.assertTrue(rsp.getStatus().isSucceed());
        UserManager.getInstance().setUser(rsp.getData().getUser(), rsp.getData().getSession());
    }

}
