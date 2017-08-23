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

public class GlideUtils {

    private GlideUtils() {
    }

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

    public static void loadPromote(Picture picture,
                                   ImageView imageView,
                                   int placeholder,
                                   int maskColor) {

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

    public static void loadPicture(Picture picture,
                                   ImageView imageView) {

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
