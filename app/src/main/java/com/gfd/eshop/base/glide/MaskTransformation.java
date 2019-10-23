package com.gfd.eshop.base.glide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import jp.wasabeef.glide.transformations.internal.Utils;

/**
 * Glide-加载图片遮盖效果
 */
public class MaskTransformation implements Transformation<Bitmap> {

    private static Paint sMaskingPaint = new Paint();
    private Context mContext;
    private BitmapPool mBitmapPool;
    private int mMaskId;

    static {
        sMaskingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
    }

    MaskTransformation(Context context, int maskId) {
        this(context, Glide.get(context).getBitmapPool(), maskId);
    }

    private MaskTransformation(Context context, BitmapPool pool, int maskId) {
        mBitmapPool = pool;
        mContext = context.getApplicationContext();
        mMaskId = maskId;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap result = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Drawable mask = Utils.getMaskDrawable(mContext, mMaskId);
        Canvas canvas = new Canvas(result);
        mask.setBounds(0, 0, width, height);
        mask.draw(canvas);
        canvas.drawBitmap(source, 0, 0, sMaskingPaint);
        return BitmapResource.obtain(result, mBitmapPool);
    }

    @Override public String getId() {
        return "MaskTransformation(maskId=" + mContext.getResources().getResourceEntryName(mMaskId) + ")";
    }

}
