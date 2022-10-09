package com.g452.bindinghelper

import android.annotation.SuppressLint
import com.g452.bindinghelper.databinding.TextListItemBinding
import com.g452.bindlibrary.adapter.BaseBindingAdapter
import com.g452.bindlibrary.adapter.BindingHolder
import com.g452.bindlibrary.extentions.onClick

class MainAdapter : BaseBindingAdapter<TextListItemBinding, String>() {
    @SuppressLint("SetTextI18n")
    override fun convert(bind: TextListItemBinding, holder: BindingHolder, item: String, pos: Int) {
        bind.apply {
            title.text = "${pos + 1}.$item"
            title.onClick {
                mOnRecycleItemClickLinter?.invoke(item, pos)
            }
        }
    }
}