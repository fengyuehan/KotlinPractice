package com.example.home.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.base.BaseRepository
import com.example.common.network.StateLiveData
import com.example.home.api.HomeService
import com.example.home.bean.ArticleData
import com.example.home.bean.BannerData
import com.example.home.bean.DailyQuestionData
import com.example.home.bean.SquareData
import com.example.home.db.AppDatabase
import com.example.home.db.DailyQuestionPagingSource
import com.example.home.db.SquarePagingDataSource
import kotlinx.coroutines.flow.Flow


class HomeRepo(private val service: HomeService,private val db:AppDatabase):BaseRepository() {
    private var mArticleType: Int = 0
    companion object{
        private const val PAGE_SIZE = 10
        val config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 5,
            initialLoadSize = 10,
            enablePlaceholders = false,
            maxSize = PAGE_SIZE * 3
        )
    }

    suspend fun getBanner(bannerLiveData:StateLiveData<List<BannerData>>){
        executeResp({
            service.getBanner()
        },bannerLiveData)
    }

    fun getDailyQuestion(): Flow<PagingData<DailyQuestionData>> {
        return Pager(config){
            DailyQuestionPagingSource(service)
        }.flow
    }

    private val pagingSourceFactory = { db.articleDao().queryLocalArticle(mArticleType) }

    /**
     * 请求首页文章，
     * Room+network进行缓存
     */
    @ExperimentalPagingApi
    fun getHomeArticle(articleType: Int): Flow<PagingData<ArticleData>> {
        mArticleType = articleType
        return Pager(
            config = config,
            remoteMediator = ArticleRemoteMediator(service, db, 1),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    /**
     * 请求广场数据
     */
    fun getSquareData(): Flow<PagingData<SquareData>> {
        return Pager(config) {
            SquarePagingDataSource(service)
        }.flow
    }

}