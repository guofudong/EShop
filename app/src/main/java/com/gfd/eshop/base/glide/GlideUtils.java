package com.gfd.eshop.base.glide;


import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gfd.eshop.R;
import com.gfd.eshop.network.entity.Picture;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

/**
 * Glide工具类
 */
public class GlideUtils {

    private GlideUtils() {
    }

    /**
     * 加载Banner图片，优先级最高 - IMMEDIATE
     * @param picture：图片地址数据实体类
     * @param imageView
     */
    public static void loadBanner(Picture picture, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(picture.getLarge())
                .error(R.drawable.ic_loading_failure_big)
                .placeholder(R.drawable.image_holder_banner)
                .centerCrop()
                .priority(Priority.IMMEDIATE)
                .crossFade(200)
                .into(imageView);
    }

    /**
     *加载图片-高斯模糊
     * @param picture
     * @param imageView
     */
    public static void loadFullPicture(Picture picture, ImageView imageView) {
        Context context = imageView.getContext();
        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(context)
                .load(picture.getSmall())
                .bitmapTransform(new BlurTransformation(context, 5));
        Glide.with(context)
                .load(picture.getLarge())
                .thumbnail(thumbnailRequest)
                .error(R.drawable.ic_loading_failure_big)
                .placeholder(R.drawable.image_holder_goods)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .bitmapTransform(new TopCropTransformation(context))
                .into(imageView);
    }

    /**
     * 加载圆形图片
     * @param picture
     * @param imageView
     * @param placeholder
     * @param maskColor
     */
    public static void loadPromote(Picture picture, ImageView imageView, int placeholder, int maskColor) {
        Context context = imageView.getContext();
        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(context)
                .load(picture.getSmall())
                .bitmapTransform(new CropCircleTransformation(context),
                        new GrayscaleTransformation(context),
                        new MaskTransformation(context, maskColor));
        DrawableRequestBuilder<String> pictureRequest = Glide
                .with(context)
                .load(picture.getMiddle())
                .thumbnail(thumbnailRequest)
                .placeholder(placeholder)
                .error(R.drawable.ic_loading_failure_big)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(context),
                        new GrayscaleTransformation(context),
                        new MaskTransformation(context, maskColor));
        pictureRequest.into(imageView);
    }

    /**
     * 加载图片
     * @param picture
     * @param imageView
     */
    public static void loadPicture(Picture picture, ImageView imageView) {
        Context context = imageView.getContext();
        DrawableRequestBuilder<String> thumbnailRequest = Glide
                .with(context)
                .load(picture.getSmall())
                .bitmapTransform(new BlurTransformation(context, 5),
                        new TopCropTransformation(context));
        DrawableRequestBuilder<String> pictureRequest = Glide
                .with(context)
                .load(picture.getMiddle())
                .thumbnail(thumbnailRequest)
                .placeholder(R.drawable.image_holder_goods)
                .error(R.drawable.ic_loading_failure_big)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new TopCropTransformation(context));
        pictureRequest.into(imageView);
    }

}
