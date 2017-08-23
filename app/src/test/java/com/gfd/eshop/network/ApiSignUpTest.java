package com.gfd.eshop.network;

import com.gfd.eshop.network.api.ApiSignUp;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ApiSignUpTest extends ApiTest {

    @Test public void signUp() throws IOException {

        ApiSignUp apiSignUp = new ApiSignUp("ycj", "123456", "ycj@163.com");
        ApiSignUp.Rsp rsp = client.execute(apiSignUp);
        Assert.assertFalse(rsp.getStatus().isSucceed());
    }

}
