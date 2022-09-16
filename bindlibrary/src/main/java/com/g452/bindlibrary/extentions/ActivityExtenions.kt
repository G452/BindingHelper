package com.g452.bindlibrary.extentions

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import java.io.Serializable

/**
 * author：G
 * time：2021/5/28 10:21
 * about：
 **/
const val mExtraData = "mExtraData"
fun <T> Context?.openActivity(className: Class<T>) {
    this?.let { startActivity(Intent(it, className)) }
}

fun <T> Context?.openActivity(className: Class<T>, mSerializable: Serializable) {
    this?.let { startActivity(Intent(it, className).apply { putExtra(mExtraData, mSerializable) }) }
}

fun <T> Context?.openActivity(className: Class<T>, mParcelable: Parcelable) {
    this?.let { startActivity(Intent(it, className).apply { putExtra(mExtraData, mParcelable) }) }
}


