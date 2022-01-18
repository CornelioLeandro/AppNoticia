package com.leandro.appnoticia.util.state

sealed class ArticleListEvent {
    object Fetch : ArticleListEvent()
}