package com.leandro.appnoticia.ui.fragments.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leandro.appnoticia.repository.NewsRepository
import com.leandro.appnoticia.ui.fragments.favorite.FavoriteViewModel
import com.leandro.appnoticia.ui.fragments.home.HomeViewModel
import com.leandro.appnoticia.ui.fragments.search.SearchViewModel

class ViewModelFactory( private val repository: NewsRepository)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(repository) as T
            else -> throw IllegalAccessException("ViewModel não encontrado")
        }
    }
}