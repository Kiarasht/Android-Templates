package com.restart.blur

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import androidx.annotation.IdRes

object Blur {
    /**
     * Blurs a view by first scaling down the incoming bitmap and using renderScrip to run a
     * standard gaussian blur.
     *
     * @param containerId Incoming layout id. It will be converted to a bitmap
     * @param activity Used to create an incoming bitmap alongside [containerId]
     * @param scaleDownFactor The incoming bitmap will be scaled down a factor before being blurred.
     */
    fun blur(@IdRes containerId: Int, activity: Activity, scaleDownFactor: Int = 4): Bitmap {
        // Create a bitmap from the given view layout
        val originalInputBitmap = activity.findViewById<View>(containerId).convertToBitmap()

        // Scale down the bitmap by a factor of 8
        val resizedInBitmap = Bitmap.createScaledBitmap(originalInputBitmap, originalInputBitmap.width / scaleDownFactor, originalInputBitmap.height / scaleDownFactor, true)

        // Create an empty bitmap with the same size of the scaled down bitmap we want to blur
        val outBitmap = Bitmap.createScaledBitmap(resizedInBitmap, resizedInBitmap.width, resizedInBitmap.height, false)

        // Create RenderScript and an Intrinsic Blur Script
        val rs = RenderScript.create(activity)
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

        // Create the in/out Allocations with the Renderscript and the in/out bitmaps
        val allIn = Allocation.createFromBitmap(rs, resizedInBitmap)
        val allOut = Allocation.createFromBitmap(rs, outBitmap)

        // Set the radius of the blur
        blurScript.setRadius(25f)

        // Perform the Renderscript
        blurScript.setInput(allIn)
        blurScript.forEach(allOut)

        // Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap)

        // Recycle the original and resized bitmap
        resizedInBitmap.recycle()
        originalInputBitmap.recycle()

        // After finishing everything, we destroy the Renderscript.
        rs.destroy()

        return outBitmap
    }

    private fun View.convertToBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }
}