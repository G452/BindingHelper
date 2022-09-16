@file:Suppress("DEPRECATION")

package com.g452.bindlibrary.extentions

import android.content.Context
import android.graphics.Point
import android.text.Html
import android.text.Spanned
import android.view.WindowManager

fun String?.toHtml(): Spanned {
    val html = if (this.isNullOrEmpty()) "" else this
    return Html.fromHtml(html)
}

fun Context.dp2px(dp: Int): Int {
    val scale: Float = resources.displayMetrics.density
    return (dp * scale + 0.5f * if (dp >= 0) 1 else -1).toInt()
}

fun Context.getStatusBarHeight(): Int {
    var statusBarHeight = 0
    try {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        statusBarHeight = if (isAllScreenDevice(this)) 80 else 63
    }
    return statusBarHeight
}

private var mHasCheckAllScreen = false
private var mIsAllScreenDevice = false
fun isAllScreenDevice(context: Context): Boolean {
    if (mHasCheckAllScreen) return mIsAllScreenDevice
    mHasCheckAllScreen = true
    mIsAllScreenDevice = false
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val point = Point()
    display.getRealSize(point)
    val width: Float
    val height: Float
    if (point.x < point.y) {
        width = point.x.toFloat()
        height = point.y.toFloat()
    } else {
        width = point.y.toFloat()
        height = point.x.toFloat()
    }
    if (height / width >= 1.97f) mIsAllScreenDevice = true
    return mIsAllScreenDevice
}