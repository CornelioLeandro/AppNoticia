package com.leandro.appnoticia.util.state

import com.leandro.appnoticia.data.local.model.Article

sealed class ArticleListState {
    data class Sucess(val list: List<Article>) : ArticleListState()
    data class ErrorMessage(val errorMessage: String) : ArticleListState()
    object Loading: ArticleListState()
    object Empty: ArticleListState()
}