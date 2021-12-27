package com.leandro.appnoticia.presenter.News

import com.leandro.appnoticia.model.NewsResponse
import com.leandro.appnoticia.model.data.NewsDataSource
import com.leandro.appnoticia.presenter.ViewHome

class NewsPresenter(val view: ViewHome.View, private val dataSource: NewsDataSource) :
    NewsHome.Presenter {
    override fun requestAll() {
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

