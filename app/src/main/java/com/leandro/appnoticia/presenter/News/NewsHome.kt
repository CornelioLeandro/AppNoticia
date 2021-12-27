package com.leandro.appnoticia.presenter.News

import com.leandro.appnoticia.model.NewsResponse

interface NewsHome {

    interface Presenter{
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}