package com.example.home.api

import com.example.common.network.BasePagingResp
import com.example.common.network.BaseResp
import com.example.home.bean.ArticleData
import com.example.home.bean.BannerData
import com.example.home.bean.DailyQuestionData
import com.example.home.bean.SquareData
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {
    @GET("article/list/{page}/json")
    suspend fun getHomeArticle(@Path("page") page: Int): BaseResp<BasePagingResp<List<ArticleData>>>


    @GET("banner/json")
    suspend fun getBanner(): BaseResp<List<BannerData>>

    @GET("wenda/list/{page}/json")
    suspend fun getDailyQuestion(@Path("page") page: Int): BaseResp<BasePagingResp<List<DailyQuestionData>>>

    @GET("user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int): BaseResp<BasePagingResp<List<SquareData>>>
}