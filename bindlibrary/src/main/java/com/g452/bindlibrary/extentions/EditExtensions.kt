package com.g452.bindlibrary.extentions

import android.annotation.SuppressLint
import android.text.Editable
import android.text.InputFilter
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import kotlin.math.roundToInt

/**
 * author：G
 * time：2022/9/13 17:38
 * about：根据产品的字符规则，控制EditText最大输入长度
 **/
fun String.getGbkSize(): Int {
    return toByteArray(charset("GBK")).size
}

@SuppressLint("SetTextI18n")
fun EditText.addCustomEditTextChangeListener(
    maxLength: Int,
    editNum: TextView? = null,
    editNumColor: String = "#ff4400",
    isGBKFilter: Boolean = true
) {
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(if (isGBKFilter) maxLength * 2 else maxLength))
    doAfterTextChanged { s ->
        changeEditNum(s, maxLength, editNum, editNumColor, isGBKFilter)
    }
}

@SuppressLint("SetTextI18n")
fun EditText.changeEditNum(
    s: Editable?,
    maxLength: Int,
    editNum: TextView? = null,
    editNumColor: String = "#ff4400",
    isGBKFilter: Boolean = true
) {
    val size = if (isGBKFilter) (s.toString().getGbkSize() * 0.5).roundToInt() else s.toString().length
    //6000
    //3000 // 80
    //0-1500 // 0-40
    if (size > maxLength) {
        var newStr = s?.substring(0, maxLength)
        val plusStr = s?.substring(maxLength, length() - 1)
        if (isGBKFilter) {
            newStr = newStr?.substring(0, (newStr.getGbkSize() * 0.5).roundToInt())
        }
        if (plusStr != null) {
            for (char in plusStr) {
                val s1 = "$newStr$char"
                if ((s1.getGbkSize() * 0.5).roundToInt() <= maxLength) {
                    newStr = s1
                } else {
                    break
                }
            }
        }
        setText(newStr)
        setSelection(text.length)
    } else editNum?.text = "<font color='${editNumColor}'>${size}</font>/${maxLength}".toHtml()

}
