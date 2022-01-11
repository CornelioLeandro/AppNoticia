package com.leandro.appnoticia.presenter

import com.leandro.appnoticia.data.local.model.Article

interface ViewHome {

    interface View{
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showArticles(article: List<Article>)
    }

    interface Favorite{
        fun showArticles(article: List<Article>)
    }
}