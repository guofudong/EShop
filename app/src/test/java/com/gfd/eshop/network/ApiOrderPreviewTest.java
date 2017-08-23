package com.gfd.eshop.network;


import com.gfd.eshop.network.api.ApiOrderPreview;

import org.junit.Test;

import java.io.IOException;

public class ApiOrderPreviewTest extends ApiSignInTest {

    @Test public void previewOrder() throws IOException {
        signIn();
        ApiOrderPreview apiOrderPreview = new ApiOrderPreview();
        client.execute(apiOrderPreview);
    }
}
