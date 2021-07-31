package com.example.home.ui

import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.common.base.BaseFragment
import com.example.home.R
import com.example.home.adapter.SquarePagingAdapter
import com.example.home.databinding.FragmentSquareBinding
import com.example.home.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class SquareFragment :BaseFragment<FragmentSquareBinding>() {
    private val mViewModel: ArticleViewModel by viewModel()

    override fun initData() {
        val pagingAdapter = SquarePagingAdapter()
        mBinding?.rvSquare?.adapter = pagingAdapter

        lifecycleScope.launchWhenCreated {
            mViewModel.squarePagingFlow().collectLatest {
                pagingAdapter.submitData(it)
            }
        }
        initListener(pagingAdapter)
    }

    private fun initListener(pagingAdapter: SquarePagingAdapter) {
        //下拉刷新
        mBinding?.swipeLayout?.setOnRefreshListener { pagingAdapter.refresh() }
        lifecycleScope.launchWhenCreated {
            pagingAdapter.loadStateFlow.collectLatest {
                //根据Paging的请求状态收缩刷新view
                mBinding?.swipeLayout?.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    override fun getLayout(): Int {
        return R.layout.fragment_square
    }


}