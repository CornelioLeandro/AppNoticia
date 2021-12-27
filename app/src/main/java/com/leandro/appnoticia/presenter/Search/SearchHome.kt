package com.leandro.appnoticia.presenter.Search

import com.leandro.appnoticia.model.NewsResponse

interface SearchHome {

    interface Presenter{
        fun search(ternm: String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}