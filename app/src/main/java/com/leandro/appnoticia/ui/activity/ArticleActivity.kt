package com.leandro.appnoticia.ui.activity


import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.leandro.appnoticia.R
import com.leandro.appnoticia.databinding.ActivityArticleBinding
import com.leandro.appnoticia.data.local.model.Article
import com.leandro.appnoticia.repository.NewsDataSource
import com.leandro.appnoticia.presenter.Favorite.FavoritePresenter
import com.leandro.appnoticia.presenter.ViewHome


class ArticleActivity : AppCompatActivity(), ViewHome.Favorite {
    lateinit var binding : ActivityArticleBinding

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        getArticle()
        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { url ->
                loadUrl(url)
            }
        }

        binding.fab.setOnClickListener {
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

}