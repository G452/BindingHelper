package com.g452.bindlibrary.compose

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

/**
 * author：G
 * time：2022/3/16 10:19
 * about：用于Compose
 **/
open class BaseComposeActivity : AppCompatActivity() {

    /**
     * 将传入content中的文字sp转化为dp
     **/
    fun ComponentActivity.setComposeContent(content: @Composable () -> Unit) {
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(LocalDensity.current.density, fontScale = 1f),
                content = content
            )
        }
    }

}