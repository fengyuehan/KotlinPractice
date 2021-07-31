package com.example.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.Constants
import com.example.common.base.BasePagingAdapter
import com.example.common.base.ItemHelper
import com.example.home.R
import com.example.home.bean.DailyQuestionData

class DailyQuestionPagingAdapter:BasePagingAdapter<DailyQuestionData>(differCallback) {
    companion object{
        val differCallback = object :DiffUtil.ItemCallback<DailyQuestionData>(){
            override fun areItemsTheSame(
                oldItem: DailyQuestionData,
                newItem: DailyQuestionData
            ): Boolean {
                return newItem.id == oldItem.id
            }

            override fun areContentsTheSame(
                oldItem: DailyQuestionData,
                newItem: DailyQuestionData
            ): Boolean {
                return newItem == oldItem
            }
        }
    }
    override fun getItemLayout(position: Int): Int {
        return R.layout.item_rv_article
    }

    override fun onItemClick(data: DailyQuestionData?) {
        ARouter.getInstance()
            .build(Constants.PATH_WEBVIEW)
            .withString(Constants.KEY_WEBVIEW_PATH, data?.link)
            .withString(Constants.KEY_WEBVIEW_TITLE, data?.title)
            .navigation()
    }

    override fun bindData(helper: ItemHelper, data: DailyQuestionData?) {
        helper.apply {
            setText(R.id.tv_article_title, data?.title)
            setText(R.id.bt_health_info_type, data?.superChapterName)
            setText(R.id.tv_article_author, data?.author)
            setText(R.id.tv_home_info_time, data?.niceShareDate)
        }
    }
}