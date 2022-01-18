package com.leandro.appnoticia.ui.fragments.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.appnoticia.data.local.db.ArticleDatabase
import com.leandro.appnoticia.data.remote.RetrofitInstance
import com.leandro.appnoticia.databinding.FragmentHomeBinding
import com.leandro.appnoticia.repository.NewsRepository
import com.leandro.appnoticia.ui.adapter.MainAdapter
import com.leandro.appnoticia.ui.fragments.base.BaseFragment
import com.leandro.appnoticia.ui.fragments.home.HomeFragmentDirections
import com.leandro.appnoticia.util.UtilQueryTextListener
import com.leandro.appnoticia.util.state.StateResource
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.rv_search
import kotlinx.android.synthetic.main.activity_search.search_news
import kotlinx.android.synthetic.main.activity_search.search_progress
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: BaseFragment<SearchViewModel, FragmentHomeBinding>() {

    private val mainAdapter by lazy { MainAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclyView()
        search()
        observerResult()
    }

    private fun observerResult() {
        viewModel.search.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is StateResource.Success -> {
                    search_progress.visibility = View.GONE
                    response.data?.let { data ->
                        mainAdapter.differ.submitList(data.articles.toList())
                    }
                }
                is StateResource.Error ->{
                    search_progress.visibility = View.GONE
                    Toast.makeText(requireContext(),
                        "Ocorreu um erro: ${response.message.toString()}"
                        , Toast.LENGTH_LONG).show()
                }

                is StateResource.Loading -> {
                    search_progress.visibility = View.VISIBLE
                }
            }
        })
    }

    fun search(){
        search_news.setOnQueryTextListener(
            UtilQueryTextListener(
                this.lifecycle
            ){
                    newText ->
                newText?.let { query ->
                    if (query.isNotEmpty()){
                        viewModel.fetchSearch(query)
                        search_progress.visibility = View.VISIBLE
                    }
                }
            }
        )
    }

    private fun setupRecyclyView() = with(binding) {
        rv_search.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
        mainAdapter.setOnClickListener { article ->
            val action =
                SearchFragmentDirections.actionSearchFragmentToWebFragment(article)
            findNavController().navigate(action)
        }
    }

    override fun getFragmentRepository(): NewsRepository =
        NewsRepository(RetrofitInstance.api, ArticleDatabase.invoke(requireContext()))

    override fun getViewModel(): Class<SearchViewModel> = SearchViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)
}