package com.leandro.appnoticia.presenter.Favorite

import com.leandro.appnoticia.model.Article
import com.leandro.appnoticia.model.NewsResponse

interface FavoriteHome {

    interface Presenter {

        fun onSuccess(articles: List<Article>)
        fun getAll()
        fun deleteArticle(article: Article?)
    }

}