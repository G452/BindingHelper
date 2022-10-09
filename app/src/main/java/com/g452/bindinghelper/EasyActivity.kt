package com.g452.bindinghelper

import com.g452.bindinghelper.databinding.ActivityEasyBinding
import com.g452.bindlibrary.BaseBindingActivity
import com.g452.bindlibrary.util.showToast
import kotlinx.android.synthetic.main.activity_easy.*

class EasyActivity : BaseBindingActivity<ActivityEasyBinding>() {
    override fun initBinding() {
        baseTitleView.setRightTextClick { "点击我了嘻嘻~".showToast() }
        contentView.text = "BaseBindingActivity的使用方法是不是非常的简洁，简单\n有什么问题可以加我的QQ：2587560197，欢迎交流！"
    }
}