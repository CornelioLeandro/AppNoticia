package com.leandro.appnoticia.repository

import com.leandro.appnoticia.data.local.model.Article
import com.leandro.appnoticia.data.local.db.ArticleDatabase
import com.leandro.appnoticia.data.remote.NewsAPI

class NewsRepository(
    private val api: NewsAPI,
    private val db: ArticleDatabase) {

    //Remote
    suspend fun getAllRemote() = api.getBreakNews()
    suspend fun  searrch(query: String) = api.searchNews(query)

    //Local
    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)
    fun getAll(): List<Article> = db.getArticleDao().getAll()
    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
}