package com.leandro.appnoticia.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.leandro.appnoticia.R
import com.leandro.appnoticia.ui.adapter.MainAdapter
import com.leandro.appnoticia.databinding.ActivityMainBinding
import com.leandro.appnoticia.data.local.model.Article
import com.leandro.appnoticia.repository.NewsDataSource
import com.leandro.appnoticia.presenter.News.NewsPresenter
import com.leandro.appnoticia.presenter.ViewHome


class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews(binding)
    }

    private fun initViews(binding: ActivityMainBinding){
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView3) as NavHostFragment
        val navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{_,destination,_ ->
            title = destination.label
        }

        binding.bottomNavigation.apply {
            setupWithNavController(navController)
            setOnNavigationItemReselectedListener {}
        }
    }

}