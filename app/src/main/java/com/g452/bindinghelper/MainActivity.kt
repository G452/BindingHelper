package com.g452.bindinghelper

import android.os.Bundle
import com.g452.bindinghelper.databinding.ActivityMainBinding
import com.g452.bindlibrary.BaseBindingActivity
import com.g452.bindlibrary.extentions.openActivity
import com.g452.bindlibrary.util.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRecyclerView.adapter = MainAdapter().apply {
            setList(titles)
            setOnRecycleItemClickLinter { s, i ->
                when (i) {
                    0 -> openActivity(EasyActivity::class.java)
                    1 -> openActivity(ComposeActivity::class.java)
                    4 -> "本页面就是单条目列表演示 ^_^".showToast()
                }
            }
        }
    }

    private var titles = listOf(
        "简单页面示例",
        "Compose页面示例",
        "Fragment页面示例",
        "DataBinding+ViewModel使用示例",
        "单条目列表示例",
        "多条目列表示例",
    )

}