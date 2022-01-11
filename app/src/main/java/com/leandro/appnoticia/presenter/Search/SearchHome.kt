package com.leandro.appnoticia.presenter.Search

import com.leandro.appnoticia.data.local.model.NewsResponse

interface SearchHome {

    interface Presenter{
        fun search(term: String)
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}