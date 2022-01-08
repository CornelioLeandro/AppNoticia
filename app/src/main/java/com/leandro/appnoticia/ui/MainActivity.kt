package com.leandro.appnoticia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.appnoticia.R
import com.leandro.appnoticia.adapter.MainAdapter
import com.leandro.appnoticia.databinding.ActivityMainBinding
import com.leandro.appnoticia.model.Article
import com.leandro.appnoticia.model.data.NewsDataSource
import com.leandro.appnoticia.presenter.News.NewsPresenter
import com.leandro.appnoticia.presenter.ViewHome


class MainActivity : AppCompatActivity(), ViewHome.View {

    private val mainAdapter by lazy {
        MainAdapter()
    }

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = NewsPresenter(this, dataSource)
        presenter.requestAll()
        configRecycle()
        clickAdapter()
    }

    private lateinit var presenter: NewsPresenter

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search_menu -> {
                Intent(this, SearchActivity::class.java).apply {
                    startActivity(this)
                }
            }
            R.id.favorite -> {
             Intent(this, FavoriteActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configRecycle() {
        with(binding.rvMain) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity, DividerItemDecoration.VERTICAL
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
       binding.mainProgress.visibility = View.VISIBLE
    }

    override fun showFailure(message: String) {
       Toast.makeText(this, message,Toast.LENGTH_LONG).show()
    }

    override fun hideProgressBar() {
        binding.mainProgress.visibility = View.GONE
    }

    override fun showArticles(article: List<Article>) {
       mainAdapter.differ.submitList(article.toList())
    }

}