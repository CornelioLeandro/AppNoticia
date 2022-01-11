package com.leandro.appnoticia.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.appnoticia.ui.adapter.MainAdapter
import com.leandro.appnoticia.databinding.ActivitySearchBinding
import com.leandro.appnoticia.data.local.model.Article
import com.leandro.appnoticia.repository.NewsDataSource
import com.leandro.appnoticia.presenter.Search.SearchPresenter
import com.leandro.appnoticia.presenter.ViewHome
import com.leandro.appnoticia.util.UtilQueryTextListener


class SearchActivity : AppCompatActivity(), ViewHome.View{

    private val mainAdapter by lazy{
        MainAdapter()
    }

    private lateinit var binding: ActivitySearchBinding

    private lateinit var presenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = SearchPresenter(this,dataSource)
        configRecycle()
        search()
        clickAdapter()
    }

    fun search(){
        binding.searchNews.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ){
                newText ->
                newText?.let { query ->
                    if (query.isNotEmpty()){
                        presenter.search(query)
                        binding.searchProgress.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun configRecycle(){
        with(binding.rvSearch) {
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
            val  intent = Intent(this, ArticleActivity::class.java)
            intent.putExtra("article", article)
            startActivity(intent)
        }
    }

    override fun showProgressBar() {
        binding.searchProgress.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.searchProgress.visibility = View.GONE
    }

    override fun showArticles(article: List<Article>) {
       mainAdapter.differ.submitList(article.toList())
    }

}