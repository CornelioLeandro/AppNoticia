package com.leandro.appnoticia.presenter.Favorite

import com.leandro.appnoticia.data.local.model.Article

interface FavoriteHome {

    interface Presenter {

        fun onSuccess(articles: List<Article>)
        fun getAll()
        fun deleteArticle(article: Article?)
    }

}