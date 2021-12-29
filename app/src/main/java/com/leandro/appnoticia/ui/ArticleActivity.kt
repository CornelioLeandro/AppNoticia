package com.leandro.appnoticia.ui


import android.webkit.WebViewClient
import com.google.android.material.snackbar.Snackbar
import com.leandro.appnoticia.R
import com.leandro.appnoticia.model.Article
import com.leandro.appnoticia.model.data.NewsDataSource
import com.leandro.appnoticia.presenter.Favorite.FavoritePresenter
import com.leandro.appnoticia.presenter.ViewHome
import kotlinx.android.synthetic.main.activity_article.*

class ArticleActivity : AbstractActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter

    override fun onInject() {
        getArticle()
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        web_view.apply {
            webViewClient = WebViewClient()
            article.url?.let { url ->
                loadUrl(url)
            }
        }

        fab.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(it, R.string.article_saved_successful, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun getArticle() {
        intent.extras?.let { articleSend ->
            article = articleSend.get("article") as Article
        }
    }

    override fun showArticles(article: List<Article>) {
    }

    override fun getLayout(): Int = R.layout.activity_article

}