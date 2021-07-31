package com.example.home.db

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.home.api.HomeService
import com.example.home.bean.SquareData

class SquarePagingDataSource(private val service: HomeService):PagingSource<Int,SquareData>() {
    override fun getRefreshKey(state: PagingState<Int, SquareData>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SquareData> {
        return try {
            val pageNum = params.key ?: 1
            val data = service.getSquareData(pageNum)
            val preKey = if (pageNum > 1) pageNum - 1 else null
            LoadResult.Page(data.data?.datas!!, prevKey = preKey, nextKey = pageNum + 1)

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}