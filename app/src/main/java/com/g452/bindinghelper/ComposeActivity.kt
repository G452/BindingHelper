package com.g452.bindinghelper

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.g452.bindlibrary.compose.BaseComposeActivity
import com.g452.bindlibrary.util.showToast
import com.g452.bindlibrary.view.BaseTitleView

class ComposeActivity : BaseComposeActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                BaseTitleView(baseTitle = "我是compose示例", rightText = "点我", rightTextColor = "#3c3c3c", rightTextClick = { "哈哈哈".showToast() })
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Greeting("BindingHelper")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
