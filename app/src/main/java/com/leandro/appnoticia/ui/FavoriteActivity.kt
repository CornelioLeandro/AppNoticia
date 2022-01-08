package com.leandro.appnoticia.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.leandro.appnoticia.R
import com.leandro.appnoticia.adapter.MainAdapter
import com.leandro.appnoticia.databinding.ActivityFavoriteBinding
import com.leandro.appnoticia.model.Article
import com.leandro.appnoticia.model.data.NewsDataSource
import com.leandro.appnoticia.presenter.Favorite.FavoritePresenter
import com.leandro.appnoticia.presenter.ViewHome


class FavoriteActivity : AppCompatActivity(), ViewHome.Favorite{

    private val mainAdapter by lazy {
        MainAdapter()
    }

    lateinit var binding: ActivityFavoriteBinding
    private lateinit var presenter:FavoritePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this,dataSource)
        presenter.getAll()
        configRecycle()
        clickAdapter()

        val itemTouchPerCallbacks = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapterPosition = viewHolder.adapterPosition
                val article = mainAdapter.differ.currentList[adapterPosition]
                presenter.deleteArticle(article)
                Snackbar.make(viewHolder.itemView,R.string.article_delete_successful,Snackbar.LENGTH_LONG).apply {
                    setAction(getString(R.string.undo)){
                        presenter.saveArticle(article)
                        mainAdapter.notifyDataSetChanged()
                    }
                    show()
                }
            }

        }
        ItemTouchHelper(itemTouchPerCallbacks).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

        presenter.getAll()
    }




    private fun configRecycle() {
        with(binding.rvFavorite) {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            addItemDecoration(
                DividerItemDecoration(
                    this@FavoriteActivity, DividerItemDecoration.VERTICAL
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

    override fun showArticles(article: List<Article>) {
     mainAdapter.differ.submitList(article.toList())
    }
}