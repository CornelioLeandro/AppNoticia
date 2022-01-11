package com.leandro.appnoticia.presenter.Search

import com.leandro.appnoticia.data.local.model.NewsResponse
import com.leandro.appnoticia.repository.NewsDataSource
import com.leandro.appnoticia.presenter.ViewHome




class SearchPresenter(val view: ViewHome.View, private val dataSource: NewsDataSource) :
    SearchHome.Presenter {


    override fun search(term: String) {
        this.view.showProgressBar()
        this.dataSource.searchNews(term, this)
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