package com.g452.bindlibrary

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
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