package com.g452.bindlibrary.extentions


import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun View.onClick(
    context: CoroutineContext = Dispatchers.Main,
    handler: suspend CoroutineScope.(v: View?) -> Unit
) {
    setOnClickListener { v ->
        GlobalScope.launch(context, CoroutineStart.DEFAULT) {
            handler(v)
        }
    }
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE
fun View.setVisible(isShow: Boolean) {
    visibility = if (isShow) View.VISIBLE else View.GONE
}

fun View.setInVisible(isShow: Boolean) {
    visibility = if (isShow) View.INVISIBLE else View.VISIBLE
}

fun Group.addOnClickListener(listener: View.OnClickListener?) {
    referencedIds.forEach { id ->
        rootView.findViewById<View>(id).setOnClickListener(listener)
    }
}

fun <T : View> T.withTrigger(delay: Long = 600): T {
    triggerDelay = delay
    return this
}


fun <T : View> T.clickWithTrigger(time: Long = 300, block: (T) -> Unit) {
    triggerDelay = time
    setOnClickListener {
        if (clickEnable()) {
            block(it as T)
        }
    }
}

private var <T : View> T.triggerLastTime: Long
    get() = if (getTag(234565432) != null) getTag(234565432) as Long else -601
    set(value) {
        setTag(234565432, value)
    }

private var <T : View> T.triggerDelay: Long
    get() = if (getTag(123454321) != null) getTag(123454321) as Long else 600
    set(value) {
        setTag(123454321, value)
    }

private fun <T : View> T.clickEnable(): Boolean {
    var flag = false
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - triggerLastTime >= triggerDelay) {
        flag = true
    }
    triggerLastTime = currentClickTime
    return flag
}

interface OnLazyClickListener : View.OnClickListener {

    override fun onClick(v: View?) {
        if (v?.clickEnable() == true) {
            onLazyClick(v)
        }
    }

    fun onLazyClick(v: View)
}

fun Context.bjxToast(message: Any): Toast = Toast
    .makeText(this, message.toString(), Toast.LENGTH_SHORT)
    .apply {
        setGravity(Gravity.CENTER, 0, 0)
        show()
    }

fun EditText.hideSoftInput() {
    val systemService = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    systemService.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showSoftInput(mContext: Activity?) {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    mContext?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

fun EditText.showSoftInput(window: Window?) {
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
}

var TextView.textColor: Int
    get() = textColor
    set(v) = setTextColor(v)

var View.backgroundResource: Int
    get() = backgroundResource
    set(v) = setBackgroundResource(v)

var View.backgroundColor: Int
    get() = backgroundColor
    set(v) = setBackgroundColor(v)

@Suppress("DEPRECATION")
var View.backgroundDrawable: Drawable?
    get() = backgroundDrawable
    set(v) = setBackgroundDrawable(v)

var ImageView.imageResource: Int
    get() = imageResource
    set(v) = setImageResource(v)

fun Context.displayMetrics(): android.util.DisplayMetrics = resources.displayMetrics

fun Context?.toast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        show()
    }

fun View.onLongClick(
    context: CoroutineContext = Dispatchers.Main,
    returnValue: Boolean = false,
    handler: suspend CoroutineScope.(v: View?) -> Unit
) {
    setOnLongClickListener { v ->
        GlobalScope.launch(context, CoroutineStart.DEFAULT) {
            handler(v)
        }
        returnValue
    }
}

var View.padding: Int
    get() = padding
    set(padding) = setPadding(padding, padding, padding, padding)

fun View.padding(left: Int, top: Int, right: Int, bottom: Int) {
    setPadding(left, top, right, bottom)
}

fun Context?.dip(value: Int): Int = if (this != null) (value * resources.displayMetrics.density).toInt() else value
fun Context?.dip(value: Float): Int = if (this != null) (value * resources.displayMetrics.density).toInt() else value.toInt()

fun View?.marginLeft(value: Int) {
    if (this == null) return
    val p = this.layoutParams
    when (p) {
        is LinearLayout.LayoutParams -> p.leftMargin = value
        is RelativeLayout.LayoutParams -> p.leftMargin = value
        is FrameLayout.LayoutParams -> p.leftMargin = value
        is TableLayout.LayoutParams -> p.leftMargin = value
        is ConstraintLayout.LayoutParams -> p.leftMargin = value
    }
}

fun View?.marginRight(value: Int) {
    if (this == null) return
    val p = this.layoutParams
    when (p) {
        is LinearLayout.LayoutParams -> p.rightMargin = value
        is RelativeLayout.LayoutParams -> p.rightMargin = value
        is FrameLayout.LayoutParams -> p.rightMargin = value
        is TableLayout.LayoutParams -> p.rightMargin = value
        is ConstraintLayout.LayoutParams -> p.rightMargin = value
    }
}

fun View?.marginTop(value: Int) {
    if (this == null) return
    val p = this.layoutParams
    when (p) {
        is LinearLayout.LayoutParams -> p.topMargin = value
        is RelativeLayout.LayoutParams -> p.topMargin = value
        is FrameLayout.LayoutParams -> p.topMargin = value
        is TableLayout.LayoutParams -> p.topMargin = value
        is ConstraintLayout.LayoutParams -> p.topMargin = value
    }
}

fun View?.marginBottom(value: Int) {
    if (this == null) return
    val p = this.layoutParams
    when (p) {
        is LinearLayout.LayoutParams -> p.bottomMargin = value
        is RelativeLayout.LayoutParams -> p.bottomMargin = value
        is FrameLayout.LayoutParams -> p.bottomMargin = value
        is TableLayout.LayoutParams -> p.bottomMargin = value
        is ConstraintLayout.LayoutParams -> p.bottomMargin = value
    }
}