package com.example.aisleassignment.moudles.core.utils.ui

import android.content.Context
import android.graphics.Bitmap
import android.icu.util.RangeValueIterator
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest

class BlurTransformation1(context: Context) : BitmapTransformation() {
    private val rs: RenderScript

    init {
        rs = RenderScript.create(context);

    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update("blur transformation".toByteArray());
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {

        val blurredBitmap = toTransform.copy(Bitmap.Config.ARGB_8888, true);

        // Allocate memory for Renderscript to work with
        val input = Allocation.createFromBitmap(
            rs,
            blurredBitmap,
            Allocation.MipmapControl.MIPMAP_FULL,
            Allocation.USAGE_SHARED
        );
        val output = Allocation.createTyped(rs, input.getType());

        // Load up an instance of the specific script that we want to use.
        val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);

        // Set the blur radius
        script.setRadius(10F);

        // Start the ScriptIntrinisicBlur
        script.forEach(output);

        // Copy the output to the blurred bitmap
        output.copyTo(blurredBitmap);

        toTransform.recycle();

        return blurredBitmap;
    }
}