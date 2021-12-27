package com.leandro.appnoticia.presenter

import com.leandro.appnoticia.model.Article

interface ViewHome {

    interface View{
        fun showProgressBar()
        fun showFailure(message: String)
        fun hodeProgressBar()
        fun showArticles(article: List<Article>)
    }
}