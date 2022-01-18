package com.leandro.appnoticia.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.leandro.appnoticia.util.state.StateResource
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    private val mainAdapter by lazy {MainAdapter()}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclyView()
        observerResult()
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding = FragmentHomeBinding.inflate(inflater,container,false)

    private fun observerResult() {
        viewModel.getAll.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is StateResource.Success -> {
                    binding.mainProgress.visibility = View.GONE
                    response.data?.let { data ->
                        mainAdapter.differ.submitList(data.articles.toList())
                    }
                }
                is StateResource.Error ->{
                    binding.mainProgress.visibility = View.GONE
                    Toast.makeText(requireContext(),
                        "Ocorreu um erro: ${response.message.toString()}"
                        ,Toast.LENGTH_LONG).show()
                }

                is StateResource.Loading -> {
                    binding.mainProgress.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun setupRecyclyView() = with(binding) {
      rvMain.apply {
          adapter = mainAdapter
          layoutManager = LinearLayoutManager(context)
          addItemDecoration(
              DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
          )
      }
        mainAdapter.setOnClickListener { article ->
            val action =
            HomeFragmentDirections.actionHomeFragmentToWebFragment(article)
            findNavController().navigate(action)
        }
    }

    override fun getFragmentRepository(): NewsRepository = NewsRepository(
        RetrofitInstance.api, ArticleDatabase.invoke(requireContext())
    )

    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java

}