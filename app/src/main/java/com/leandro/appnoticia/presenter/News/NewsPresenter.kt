package com.leandro.appnoticia.presenter.News

import com.leandro.appnoticia.model.NewsResponse
import com.leandro.appnoticia.model.data.NewsDataSource
import com.leandro.appnoticia.presenter.ViewHome

class NewsPresenter(val view: ViewHome.View, private val dataSource: NewsDataSource) :
    NewsHome.Presenter {

    override fun requestAll() {
       this.view.showProgressBar()
        this.dataSource.getBreakNews(this)
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        this.view.showArticles(newsResponse.articles)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onComplete() {
        this.view.hideProgressBar()
    }

}

