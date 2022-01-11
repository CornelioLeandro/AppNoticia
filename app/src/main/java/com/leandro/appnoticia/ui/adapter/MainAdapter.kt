package com.leandro.appnoticia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.leandro.appnoticia.databinding.ItemMenuMainBinding
import com.leandro.appnoticia.data.local.model.Article

class MainAdapter : RecyclerView.Adapter<MainAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemMenuMainBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ItemMenuMainBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        with(holder) {
            with(differ.currentList[position]) {
                Glide.with(holder.itemView.context).load(urlToImage).into(binding.mainArticleImage)
                binding.mainTextTitle.text = author ?: source?.name
                binding.mainTextSource.text = source?.name ?: author
                binding.mainTextDescription.text = description
                binding.mainTextPublishedat.text = publishedAt

                holder.itemView.setOnClickListener {
                    onItemClickListener?.let { click ->
                        click(this)
                    }
                }
            }
        }
    }


        private var onItemClickListener: ((Article) -> Unit)? = null

        fun setOnClickListener(listener: (Article) -> Unit) {
            onItemClickListener = listener
        }
    }