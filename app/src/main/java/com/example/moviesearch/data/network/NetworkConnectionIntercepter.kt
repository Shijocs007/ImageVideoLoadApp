package com.example.moviesearch.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.example.moviesearch.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.HttpUrl



class NetworkConnectionIntercepter(context : Context) : Interceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {

        if(!isInternetAvailable()) {
            throw NoInternetException("Make sure network is active")
        }

        var request = chain.request()
        val url = request.url().newBuilder().addQueryParameter("api_key", "DEMO_KEY").build()
        request = request.newBuilder().url(url).build()

        return chain.proceed(request)
    }

    private fun isInternetAvailable() : Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }
}