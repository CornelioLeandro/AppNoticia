package com.leandro.appnoticia.presenter.Search

import com.leandro.appnoticia.model.NewsResponse
import com.leandro.appnoticia.model.data.NewsDataSource
import com.leandro.appnoticia.presenter.ViewHome

class SearchPresenter(val view: ViewHome.View, private val dataSource: NewsDataSource): SearchHome.Presenter {
    override fun search(ternm: String) {
        TODO("Not yet implemented")
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onComplete() {
        TODO("Not yet implemented")
    }
}