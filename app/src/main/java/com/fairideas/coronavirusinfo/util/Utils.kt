package com.fairideas.coronavirusinfo.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast

/**
 * Created by illia-herman on 25.02.20.
 */
fun applyViewVisible(vararg view: View) {
    view.forEach {
        it.visibility = View.VISIBLE
    }
}

fun applyViewInVisible(vararg view: View) {
    view.forEach {
        it.visibility = View.INVISIBLE
    }
}

fun applyViewGone(vararg view: View) {
    view.forEach {
        it.visibility = View.GONE
    }
}

fun Context.showShortToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun createBitmap(drawable: Drawable): Bitmap {
    val bitmap = Bitmap.createBitmap(
        drawable.getIntrinsicWidth(),
        drawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}
