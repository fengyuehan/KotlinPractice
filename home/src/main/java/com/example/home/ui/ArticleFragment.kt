package com.example.home.ui

import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.example.common.base.BaseFragment
import com.example.common.network.IStateObserver
import com.example.common.widget.FooterAdapter
import com.example.home.R
import com.example.home.adapter.HomeArticlePagingAdapter
import com.example.home.bean.BannerData
import com.example.home.databinding.FragmentArticleBinding
import com.example.home.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment:BaseFragment<FragmentArticleBinding>() {
    private lateinit var homeArticlePagingAdapter:HomeArticlePagingAdapter
    private val mViewModel:ArticleViewModel by viewModel()

    @ExperimentalPagingApi
    override fun initData() {
        homeArticlePagingAdapter = HomeArticlePagingAdapter()
        mBinding?.rv?.adapter = homeArticlePagingAdapter.withLoadStateFooter(FooterAdapter{
            homeArticlePagingAdapter.retry()
        })

        mViewModel.getBannerData()
        mViewModel.bannerLiveData.observe(this,object :IStateObserver<List<BannerData>>(null){
            override fun onDataChange(data: List<BannerData>?) {
                data?.let {
                    homeArticlePagingAdapter.addBannerList(it)
                    homeArticlePagingAdapter.notifyItemChanged(0)
                }
            }

            override fun onReload(v: View?) {
                TODO("Not yet implemented")
            }

            override fun onError(error: Throwable?) {
                TODO("Not yet implemented")
            }

            override fun onDataEmpty() {
                TODO("Not yet implemented")
            }

        })
        /**
         * collectLatest:有新值发出时，如果此时上个收集尚未完成，则会取消掉上个值的收集操作
         *
         */
        lifecycleScope.launchWhenCreated {
            mViewModel.articlePagingFlow().collectLatest {data ->
                homeArticlePagingAdapter.submitData(data)
            }
        }
        initListener()
    }

    private fun initListener() {
        mBinding?.srl?.setOnRefreshListener {
            mViewModel.getBannerData()
            homeArticlePagingAdapter.refresh()
        }

        lifecycleScope.launchWhenCreated {
            homeArticlePagingAdapter.loadStateFlow.collectLatest {
                mBinding?.srl?.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_article
    }
}