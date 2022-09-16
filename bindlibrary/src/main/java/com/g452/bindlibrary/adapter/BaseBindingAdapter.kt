package com.g452.bindlibrary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType

/**
 * author：G
 * time：2021/4/29 15:01
 * about：DataBinding 单个item  Adapter父类 多个请用BaseMultiBindingAdapter
 **/
@Suppress("UNCHECKED_CAST")
abstract class BaseBindingAdapter<Binding : ViewDataBinding, ITEM> : RecyclerView.Adapter<BindingHolder>(), AutoUpdatableAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder = BindingHolder(parent.getViewBinding())

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        convert(holder.binding as Binding, holder, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    protected abstract fun convert(bind: Binding, holder: BindingHolder, item: ITEM, pos: Int)

    override fun getItemCount(): Int = mListData.size

    fun getItem(position: Int): ITEM = mListData[position]

    fun ViewGroup?.getViewBinding(): Binding {
        return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<Binding>>()[0].let {
            it.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java).let { method ->
                method.invoke(null, LayoutInflater.from(this?.context), this, false) as Binding
            }
        }
    }

    var mListData = arrayListOf<ITEM>()
    private var oldListData = arrayListOf<ITEM>()
    val listCallback by lazy { ListCallback() }

    open fun setList(items: List<ITEM>?) {
        if (items != null) {
            this.mListData = ArrayList(items)
            autoNotify(listCallback, ArrayList(oldListData), ArrayList(items)) { o, n -> o === n }
            oldListData = ArrayList(items)
        }
    }

    open fun setListNotify(items: List<ITEM>?) {
        if (items != null) {
            this.mListData = ArrayList(items)
            notifyDataSetChanged()
        }
    }

    var mOnRecycleItemClickLinter: ((ITEM, Int) -> Unit)? = null
    fun setOnRecycleItemClickLinter(mOnRecycleItemClickLinter: ((ITEM, Int) -> Unit)) {
        this.mOnRecycleItemClickLinter = mOnRecycleItemClickLinter
    }

    var mOnRecycleViewClickLinter: ((ITEM, List<View>) -> Unit)? = null
    fun setOnRecycleViewClickLinter(mOnRecycleViewClickLinter: ((ITEM, List<View>) -> Unit)) {
        this.mOnRecycleViewClickLinter = mOnRecycleViewClickLinter
    }
}


