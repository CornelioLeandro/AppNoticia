package com.leandro.appnoticia.data.local.db

import androidx.room.*
import com.leandro.appnoticia.data.local.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInsert(article: Article) :Long

    @Query("SELECT * FROM articles")
    fun getAll(): List<Article>

    @Delete
    suspend fun delete(article: Article)
}