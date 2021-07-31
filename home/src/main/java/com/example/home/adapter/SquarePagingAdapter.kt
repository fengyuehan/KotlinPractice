package com.example.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.Constants
import com.example.common.base.BasePagingAdapter
import com.example.common.base.ItemHelper
import com.example.home.R
import com.example.home.bean.SquareData

class SquarePagingAdapter:BasePagingAdapter<SquareData>(differCallback) {
    companion object{
        val differCallback = object :DiffUtil.ItemCallback<SquareData>(){
            override fun areItemsTheSame(oldItem: SquareData, newItem: SquareData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SquareData, newItem: SquareData): Boolean {
                return oldItem == newItem
            }

        }
    }
    override fun getItemLayout(position: Int): Int {
        return R.layout.item_rv_article
    }

    override fun onItemClick(data: SquareData?) {
        ARouter.getInstance()
            .build(Constants.PATH_WEBVIEW)
            .withString(Constants.KEY_WEBVIEW_PATH,data?.link)
            .withString(Constants.KEY_WEBVIEW_TITLE,data?.title)
            .navigation()

    }

    override fun bindData(helper: ItemHelper, data: SquareData?) {
        helper.setText(R.id.tv_article_title, data?.title)
        helper.setText(R.id.bt_health_info_type, data?.superChapterName)
        helper.setText(R.id.tv_home_info_time, data?.niceDate)
        helper.setText(R.id.tv_article_author, data?.shareUser)
    }
}