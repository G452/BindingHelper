package com.g452.bindlibrary

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import com.g452.bindlibrary.util.UltimateBar
import java.lang.reflect.ParameterizedType


/**
 * author：G
 * time：2021/5/14 15:38
 * about：用于DataBinding模式的 BaseBindingActivity
 **/
@Suppress("UNCHECKED_CAST")
open class BaseBindingActivity<Binding : ViewDataBinding> : AppCompatActivity() {

    val binding: Binding by lazy(mode = LazyThreadSafetyMode.NONE) { layoutInflater.getViewBinding() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UltimateBar.newImmersionBuilder().applyNav(false).build(this).apply()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        binding.lifecycleOwner = this
        setContentView(binding.root)
        initBinding()
    }

    open fun initBinding() {}

    fun <Binding : ViewDataBinding> LayoutInflater?.getViewBinding(): Binding {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<Binding>>()[0].let {
            it.getDeclaredMethod("inflate", LayoutInflater::class.java).let { method ->
                method.invoke(null, this) as Binding
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

}