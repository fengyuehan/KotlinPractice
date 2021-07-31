package com.example.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.Constants
import com.example.common.base.BasePagingAdapter
import com.example.common.base.ItemHelper
import com.example.home.R
import com.example.home.bean.ArticleData
import com.example.home.bean.BannerData
import com.example.home.databinding.ItemBannerBinding
import com.example.home.databinding.ItemRvArticleBinding
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

class HomeArticlePagingAdapter :PagingDataAdapter<ArticleData,RecyclerView.ViewHolder>(differCallback) {
    private var bannerList:List<BannerData> = ArrayList()

    fun addBannerList(list: List<BannerData>){
        bannerList = list;
    }


    companion object{
        const val TYPE_BANNER = 0
        const val TYPE_ARTICLE = 1;

        val differCallback = object : DiffUtil.ItemCallback<ArticleData>(){
            override fun areItemsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ArticleData, newItem: ArticleData): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_BANNER){
            (holder as BannerVH).bindData(bannerList)
        }else{
            getItem(position -1)?.let {
                (holder as ArticleVH).bindData(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_BANNER){
            return BannerVH(ItemBannerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }
        val articleVH  = ArticleVH(
            ItemRvArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        articleVH.itemView.setOnClickListener {
            /**
             * layoutPosition:与用户上看到的一样的
             * adapterPosition:如果用的是notifyDataSetChanged，在绘制完成之前，会返回-1，notifyItemInserted（）则会正确的获取
             */
            val data = getItem(articleVH.layoutPosition -1)
            ARouter.getInstance()
                .build(Constants.PATH_WEBVIEW)
                .withString(Constants.KEY_WEBVIEW_PATH,data?.link)
                .withString(Constants.KEY_WEBVIEW_TITLE,data?.title)
                .navigation()

        }
        return articleVH
    }

    override fun getItemViewType(position: Int): Int {
        if(position == 0){
            return TYPE_BANNER
        }
        return TYPE_ARTICLE
    }

    /**
     * internal限制只能在这个模块使用，其他的模块不能使用
     */
    internal class BannerVH(val binding:ItemBannerBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(data: List<BannerData>){
            binding.bannerArticle.adapter = object :BannerImageAdapter<BannerData>(data){
                override fun onBindView(
                    holder: BannerImageHolder?,
                    data: BannerData?,
                    position: Int,
                    size: Int
                ) {
                    holder!!.imageView.load(data?.imagePath){
                        placeholder(R.mipmap.img_placeholder)
                    }
                }

            }
        }
    }

    internal class ArticleVH(val binding: ItemRvArticleBinding):RecyclerView.ViewHolder(binding.root){
        fun bindData(data: ArticleData){
            setText(binding.tvArticleTitle,data.title)
            setText(binding.btHealthInfoType,data.superChapterName)
            setText(binding.tvHomeInfoTime,data.niceDate)
            if (data.author.isEmpty()){
                setText(binding.tvArticleAuthor,data.shareUser)
            }else{
                setText(binding.tvArticleAuthor,data.author)
            }
        }

        fun setText(view:TextView,text:String){
            view.text = text
        }
    }

}