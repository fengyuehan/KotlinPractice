package com.example.common.base

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.common.R

abstract class BasePagingAdapter<T:Any>(diffUtil: DiffUtil.ItemCallback<T>):
    PagingDataAdapter<T, RecyclerView.ViewHolder>(diffUtil) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        (holder as BasePagingAdapter<*>.BaseViewHolder).bindNormalData(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder = BaseViewHolder(parent,viewType)
        holder.itemView.setOnClickListener {
            onItemClick(getItem(holder.layoutPosition))
        }
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return getItemLayout(position)
    }

    /**
     * 子类获取layout
     */
    protected abstract fun getItemLayout(position: Int): Int

    inner class BaseViewHolder(parent: ViewGroup,layout:Int):RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(layout,parent,false)
    ){
        private val helper:ItemHelper = ItemHelper(this)
        fun bindNormalData(item: Any?) {
            bindData(helper, item as T)
        }
    }

    /**
     * itemView的点击事件，子类实现
     */
    protected abstract fun onItemClick(data: T?)

    /**
     * 子类绑定数据
     */
    protected abstract fun bindData(helper: ItemHelper, data: T?)
}

class ItemHelper(holder: BasePagingAdapter<*>.BaseViewHolder) {
    private val itemView = holder.itemView
    private val viewCache = SparseArray<View>()

    private fun findViewById(viewId: Int): View {
        var view = viewCache.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            if (view == null) {
                throw NullPointerException("$viewId can not find this item！")
            }
            viewCache.put(viewId, view)
        }
        return view
    }

    /**
     * TextView设置内容
     */
    fun setText(viewId: Int, text: CharSequence?): ItemHelper {
        (findViewById(viewId) as TextView).text = text
        return this
    }

    /**
     * Coil加载图片
     */
    fun bindImgGlide(viewId: Int, url: String) {
        val imageView: ImageView = findViewById(viewId) as ImageView
        imageView.load(url) {
            placeholder(R.mipmap.img_placeholder)
        }

    }
}
