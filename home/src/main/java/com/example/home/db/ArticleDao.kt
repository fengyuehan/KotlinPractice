package com.example.home.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.home.bean.ArticleData

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleDataList: List<ArticleData>)

    @Query("SELECT * FROM tab_article WHERE articleType =:articleType")
    fun queryLocalArticle(articleType: Int): PagingSource<Int, ArticleData>

    @Query("DELETE FROM tab_article WHERE articleType=:articleType")
    suspend fun clearArticleByType(articleType: Int)
}