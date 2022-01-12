package com.leandro.appnoticia.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leandro.appnoticia.data.local.model.NewsResponse
import com.leandro.appnoticia.repository.NewsRepository
import com.leandro.appnoticia.util.state.StateResource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class HomeViewModel constructor(private val repository: NewsRepository) :ViewModel(){

    private val _getAll = MutableLiveData<StateResource<NewsResponse>>()
    val getAll: LiveData<StateResource<NewsResponse>> get() = _getAll

    init {
        fetchAll()
    }

    private fun fetchAll() = viewModelScope.launch {
        safeFetchAll()
    }

    private suspend fun safeFetchAll() {
        _getAll.value = StateResource.Loading()
        try {
            val response = repository.getAllRemote()
            _getAll.value = handleResponse(response)
        }catch (e: Exception){
            _getAll.value = StateResource.Error("Artigo não encontrado: ${e.message}")
        }
    }

    private fun handleResponse(response: Response<NewsResponse>): StateResource<NewsResponse>? {
        if (response.isSuccessful){
            response.body()?.let{ values ->
                return StateResource.Success(values)
            }
        }
        return  StateResource.Error(response.message())
    }
}