package com.leandro.appnoticia.presenter.Favorite

import com.leandro.appnoticia.model.Article
import com.leandro.appnoticia.model.data.NewsDataSource
import com.leandro.appnoticia.presenter.ViewHome

class FavoritePresenter(
    val view: ViewHome.Favorite,
    private val datasource: NewsDataSource
) : FavoriteHome.Presenter {

    fun saveArticle(article: Article) {
        this.datasource.saveArticle(article)
    }

    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }

    override fun getAll() {
      this.datasource.getAllArticle(this)
    }

    override fun deleteArticle(article: Article?) {
      this.datasource.deleteArticle(article)
    }
}