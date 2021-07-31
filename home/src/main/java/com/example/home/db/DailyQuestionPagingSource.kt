package com.example.home.db

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.home.api.HomeService
import com.example.home.bean.DailyQuestionData
import java.lang.Exception

/**
 * PagingSource需要两个泛型类型，第一个类型表示页数的数据类型，第二个表示每一项的数据类型
 */
class DailyQuestionPagingSource(val service: HomeService) :PagingSource<Int,DailyQuestionData>(){
    override fun getRefreshKey(state: PagingState<Int, DailyQuestionData>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DailyQuestionData> {
        return try {
            val pageNum = params.key?:1
            val data = service.getDailyQuestion(pageNum)
            val preKey = if (pageNum > 1) pageNum-1 else null
            LoadResult.Page(data.data?.datas!!, prevKey = preKey, nextKey = pageNum + 1)
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

}