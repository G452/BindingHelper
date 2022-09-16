package com.g452.bindlibrary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * author：G
 * time：2021/5/6 9:52
 * about：DataBinding 多item Adapter父类 单个请用BaseBindingAdapter
 **/
@Suppress("UNCHECKED_CAST")
abstract class BaseMultiBindingAdapter<ITEM> : RecyclerView.Adapter<BindingHolder>(), AutoUpdatableAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder = BindingHolder(onCreateMultiViewHolder(parent, viewType))

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        convert(holder.binding,holder, getItem(position), position)
        holder.binding.executePendingBindings()
    }

    abstract fun onCreateMultiViewHolder(parent: ViewGroup, viewType: Int): ViewDataBinding

    abstract fun convert(bind: ViewDataBinding, holder: BindingHolder, item: ITEM, position: Int)

    protected fun <Binding : ViewDataBinding> ViewGroup?.loadLayout(mClass: Class<Binding>): Binding {
        return mClass.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java).let {
            it.invoke(null, LayoutInflater.from(this?.context), this, false) as Binding
        }
    }

    override fun getItemCount(): Int = mListData.size

    fun getItem(position: Int): ITEM = mListData[position]

    var mListData = arrayListOf<ITEM>()
    private var oldListData = arrayListOf<ITEM>()
    val listCallback by lazy { ListCallback() }

    open fun setList(items: ArrayList<ITEM>?) {
        if (items != null) {
            this.mListData = items
            autoNotify(listCallback, ArrayList(oldListData), ArrayList(items)) { o, n -> o === n }
            oldListData = items
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
}