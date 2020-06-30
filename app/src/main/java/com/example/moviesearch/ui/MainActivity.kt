package com.example.moviesearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.moviesearch.R
import com.example.moviesearch.databinding.ActivityMainBinding
import com.example.moviesearch.utils.toast
import com.example.moviesearch.viewmodel.MainViewModel
import com.example.moviesearch.viewmodel.MainViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory : MainViewModelFactory by instance()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binding.viewmodel = viewModel

        viewModel.getErrorMessage().observe(this, Observer {
            it.let {
                toast(it)
            }
        })
    }
}
