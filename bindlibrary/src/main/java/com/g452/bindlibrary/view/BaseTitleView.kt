package com.g452.bindlibrary.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.ConstraintLayout
import com.g452.bindlibrary.R
import com.g452.bindlibrary.extentions.dp2px
import com.g452.bindlibrary.extentions.getStatusBarHeight
import com.g452.bindlibrary.extentions.textColor

/**
 * author：G
 * time：2021/3/2 11:54
 * about：标题View
 **/
@Suppress("DEPRECATION")
class BaseTitleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = -1) :
    ConstraintLayout(context, attrs, defStyleAttr) {
    val backView: ImageView by lazy { initBackView() }
    val titleView: TextView by lazy { initTitleView() }
    val rightTextButton: TextView by lazy { initRightTextView() }
    val rightButton: ImageView by lazy { initRightImgView() }

    init {
        isTopPadding(true)
        setBackClick { (context as Activity).finish() }
        initAttributeSet(attrs, defStyleAttr)
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun initAttributeSet(attrs: AttributeSet?, defStyleAttr: Int) {
        attrs?.let {
            context.obtainStyledAttributes(attrs, R.styleable.BaseTitleView, defStyleAttr, 0).let { attr ->
                setRightTextColor(attr.getInt(R.styleable.BaseTitleView_rightTextColor, -1))
                isTopPadding(attr.getBoolean(R.styleable.BaseTitleView_isTopPadding, true))
                attr.getString(R.styleable.BaseTitleView_baseTitle)?.let { setTitle(it) }
                attr.getString(R.styleable.BaseTitleView_rightText)?.let { if (it.isNotEmpty()) setRightText(it) }
                attr.getResourceId(R.styleable.BaseTitleView_rightImg, -1).let { if (it != -1) setRightImg(it) }
                attr.getResourceId(R.styleable.BaseTitleView_backImg, -1).let { if (it != -1) setBackImg(it) }
            }
        }
    }

    /**
     * 初始化返回按钮
     **/
    private fun initBackView(): ImageView {
        return ImageView(context).apply {
            setImageResource(R.drawable.ic_black_back)
            val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, context.dp2px(50)).apply {
                topToTop = top
                leftToLeft = left
            }
            setPadding(context.dp2px(10), 0, context.dp2px(10), 0)
            addView(this, layoutParams)
        }
    }

    /**
     * 初始化标题
     **/
    private fun initTitleView(): TextView {
        return TextView(context).apply {
            val layoutParams = LayoutParams(0, context.dp2px(50)).apply {
                rightMargin = context.dp2px(70)
                leftMargin = context.dp2px(70)
                topToTop = top
                bottomToBottom = bottom
                leftToLeft = left
                rightToRight = right
            }
            textColor = Color.parseColor("#333333")
            gravity = Gravity.CENTER
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
            textSize = 18f
            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            addView(this, layoutParams)
        }
    }

    /**
     * 初始化右侧图片按钮
     **/
    private fun initRightImgView(): ImageView {
        return ImageView(context).apply {
            val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, context.dp2px(50)).apply {
                topToTop = top
                bottomToBottom = bottom
                rightToRight = right
            }
            setPadding(context.dp2px(10), 0, context.dp2px(16), 0)
            addView(this, layoutParams)
        }
    }

    /**
     * 初始化右侧文字按钮
     **/
    private fun initRightTextView(): TextView {
        return TextView(context).apply {
            val layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, context.dp2px(50)).apply {
                topToTop = top
                bottomToBottom = bottom
                rightToRight = right
            }
            textSize = 15f
            gravity = Gravity.CENTER
            textColor = Color.parseColor("#333333")
            setPadding(context.dp2px(10), 0, context.dp2px(16), 0)
            addView(this, layoutParams)
        }
    }


    /**
     * 设置标题
     */
    fun setTitle(title: Any) {
        titleView.text = title.toString()
    }


    /**
     * 是否设置顶部Padding
     * 默认开启
     */
    fun isTopPadding(isShow: Boolean) {
        setPadding(0, if (isShow) context.getStatusBarHeight() else 0, 0, 0)
    }

    /**
     * 右侧图片
     */
    fun setRightImg(img: Int) {
        if (img == -1) return
        rightButton.setImageResource(img)
    }

    /**
     * 右侧文字按钮
     */
    fun setRightText(text: String) {
        rightTextButton.text = text
    }


    /**
     * 设置右侧字体颜色
     **/
    fun setRightTextColor(it: Int) {
        if (it == -1) return
        rightTextButton.textColor = it
    }


    /**
     * 右侧图片点击
     */
    fun setRightImgClick(click: (View) -> Unit) {
        rightButton.setOnClickListener(click)
    }

    /**
     * 右侧图片点击
     */
    fun setRightTextClick(click: (View) -> Unit) {
        rightTextButton.setOnClickListener(click)
    }

    /**
     * 返回键点击
     * 默认是 finish()
     */
    fun setBackClick(click: (View) -> Unit) {
        backView.setOnClickListener(click)
    }

    /**
     * 返回键图片资源
     */
    fun setBackImg(img: Int) {
        backView.setImageResource(img)
    }

    // TODO: 2021/3/2 还需要什么可以在下面拓展

}

@Composable
fun BaseTitleView(
    baseTitle: String = "",
    rightTextColor: Int = -1,
    isTopPadding: Boolean = true,
    rightText: String = "",
    rightImg: Int = -1,
    backImg: Int = -1,
    rightTextClick: (View) -> Unit = {},
    rightButtonClick: (View) -> Unit = {},
    backImgClick: (View) -> Unit = { (it.context as Activity).finish() },
) {
    AndroidView(
        factory = { ctx ->
            BaseTitleView(ctx).apply {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setTitle(baseTitle)
                isTopPadding(isTopPadding)
                if (rightText.isNotEmpty()) setRightText(rightText)
                if (rightText.isNotEmpty()) setRightTextClick(rightTextClick)
                if (rightImg != -1) setRightImg(rightImg)
                if (rightImg != -1) setRightImgClick(rightButtonClick)
                if (rightTextColor != -1) setRightTextColor(rightTextColor)
                if (backImg != -1) setBackImg(backImg)
                setBackClick(backImgClick)
            }
        }
    )
}
