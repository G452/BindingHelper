package com.g452.bindlibrary.util

import android.util.Log
import android.view.Gravity
import android.widget.Toast
import com.g452.bindlibrary.App

/**
 * time：2021/2/25 17:08
 * about：toast优化
 */
object ToastUtil {

    private var toast: Toast? = null
    fun showToast(content: Any?) {
        try {
            if (content == null || content == "") return
            if (toast == null) {
                toast = Toast.makeText(App.context, content.toString(), Toast.LENGTH_SHORT)
                toast?.setGravity(Gravity.CENTER, 0, 0)
            } else {
                toast?.setText(content.toString())
            }
            toast?.show()
        } catch (e: Exception) {
            Log.d("--ToastUtil--Exception",e.toString())
            e.printStackTrace()
        }
    }
}

fun Any?.showToast() {
    Log.d("--ToastUtil--",this.toString())
    ToastUtil.showToast(this)
}