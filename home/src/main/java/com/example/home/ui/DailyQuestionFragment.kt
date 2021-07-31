package com.example.home.ui

import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.common.base.BaseFragment
import com.example.common.widget.FooterAdapter
import com.example.home.R
import com.example.home.adapter.DailyQuestionPagingAdapter
import com.example.home.databinding.FragmentDailyQuestBinding
import com.example.home.viewmodel.ArticleViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class DailyQuestionFragment:BaseFragment<FragmentDailyQuestBinding>() {
    private val mViewModel:ArticleViewModel by viewModel()

    private val dailyQuestionPagingAdapter = DailyQuestionPagingAdapter()

    override fun initData() {
        initRv()
        initListener()
        loadData()
    }

    private fun loadData() {
        /**
         * collectLatest 是末端操作符，收集 Flow 在 Repositories 层发射出来的数据，在一段时间内发送多次数据，只会接受最新的一次发射过来的数据
         */
        lifecycleScope.launchWhenCreated {
            mViewModel.getDailyQuestion().collectLatest {
                dailyQuestionPagingAdapter.submitData(it)
            }
        }
    }

    private fun initListener() {
        mBinding?.srl?.setOnRefreshListener {
            dailyQuestionPagingAdapter.refresh()
        }
        lifecycleScope.launchWhenCreated {
            dailyQuestionPagingAdapter.loadStateFlow.collectLatest {
                mBinding?.srl?.isRefreshing = it.refresh is LoadState.Loading
            }
        }
    }

    private fun initRv() {
        mBinding?.rv?.layoutManager = LinearLayoutManager(context)
        mBinding?.rv?.adapter = dailyQuestionPagingAdapter.withLoadStateFooter(
            FooterAdapter{
                dailyQuestionPagingAdapter.retry()
            }
        )
    }

    override fun getLayout(): Int {
        return R.layout.fragment_daily_quest
    }
}