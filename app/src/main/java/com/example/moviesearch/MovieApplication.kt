package com.example.moviesearch

import android.app.Application
import com.example.moviesearch.data.db.AppDataBase
import com.example.moviesearch.data.network.MyApi
import com.example.moviesearch.data.network.NetworkConnectionIntercepter
import com.example.moviesearch.data.repository.MainRepository
import com.example.moviesearch.viewmodel.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MovieApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MovieApplication))

        bind() from singleton { NetworkConnectionIntercepter(instance())}
        bind() from singleton { MyApi(instance())}
        bind() from singleton { AppDataBase(instance())}
        bind() from singleton { MainRepository(instance(), instance()) }
        bind() from provider { MainViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}