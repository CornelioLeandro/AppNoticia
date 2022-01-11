package com.leandro.appnoticia.repository

import com.leandro.appnoticia.data.local.model.Article
import com.leandro.appnoticia.data.local.db.ArticleDatabase

class NewsRepository(private val db: ArticleDatabase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
}