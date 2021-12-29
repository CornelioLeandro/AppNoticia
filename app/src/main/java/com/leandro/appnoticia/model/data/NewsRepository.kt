package com.leandro.appnoticia.model.data

import com.leandro.appnoticia.model.Article
import com.leandro.appnoticia.model.db.ArticleDatabase

class NewsRepository(private val db:ArticleDatabase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
}