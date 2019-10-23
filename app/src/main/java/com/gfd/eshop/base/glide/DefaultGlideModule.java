package com.gfd.eshop.base.glide;


import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

public class DefaultGlideModule implements GlideModule {

    @Override public void applyOptions(Context context, GlideBuilder builder) {
        // 增加图片质量 (Glide默认使用RGB565对图片解码)
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override public void registerComponents(Context context, Glide glide) {
    }

}
