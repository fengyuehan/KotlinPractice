package com.example.home.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.common.base.BaseViewModel
import com.example.common.network.StateLiveData
import com.example.home.bean.ArticleData
import com.example.home.bean.BannerData
import com.example.home.bean.DailyQuestionData
import com.example.home.bean.SquareData
import com.example.home.db.DailyQuestionPagingSource
import com.example.home.repo.HomeRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ArticleViewModel(val repo:HomeRepo):BaseViewModel() {
    val bannerLiveData = StateLiveData<List<BannerData>>()


    fun getBannerData(){
        viewModelScope.launch (Dispatchers.IO){
            repo.getBanner(bannerLiveData)
        }
    }

    /**
     * cachedIn() 函数，这是用于将服务器返回的数据在 viewModelScope 这个作用域内进行缓存，假如手机横竖屏发生了旋转导致 Activity
     * 重新创建，Paging 3 就可以直接读取缓存中的数据，而不用重新发起网络请求了。
     */
    fun getDailyQuestion(): Flow<PagingData<DailyQuestionData>>{
        return repo.getDailyQuestion().cachedIn(viewModelScope)
    }


    @ExperimentalPagingApi
    fun articlePagingFlow():Flow<PagingData<ArticleData>>{
        return repo.getHomeArticle(1).cachedIn(viewModelScope)
    }

    /**
     * 查询广场数据
     */
    fun squarePagingFlow(): Flow<PagingData<SquareData>> =
        repo.getSquareData().cachedIn(viewModelScope)
}