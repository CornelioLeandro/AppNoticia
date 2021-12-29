package com.leandro.appnoticia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.appnoticia.R
import com.leandro.appnoticia.adapter.MainAdapter
import com.leandro.appnoticia.model.Article
import com.leandro.appnoticia.model.data.NewsDataSource
import com.leandro.appnoticia.presenter.Search.SearchPresenter
import com.leandro.appnoticia.presenter.ViewHome
import com.leandro.appnoticia.util.UtilQueryTextListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AbstractActivity(), ViewHome.View{

    private val mainAdapter by lazy{
        MainAdapter()
    }

    private lateinit var presenter: SearchPresenter

    override fun onInject() {
     val dataSource = NewsDataSource(this)
        presenter = SearchPresenter(this,dataSource)
        configRecycle()
        search()
        clickAdapter()
    }

    fun search(){
        search_news.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ){
                newText ->
                newText?.let { query ->
                    if (query.isNotEmpty()){
                        presenter.search(query)
                        search_progress.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun configRecycle(){
        with(rv_search) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@SearchActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@SearchActivity, DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun clickAdapter(){
        mainAdapter.setOnClickListener { article ->
            val  intent = Intent(this,ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }

    override fun showProgressBar() {
        search_progress.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        search_progress.visibility = View.GONE
    }

    override fun showArticles(article: List<Article>) {
       mainAdapter.differ.submitList(article.toList())
    }

    override fun getLayout(): Int = R.layout.activity_search

}