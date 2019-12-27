package com.spdigital.weatherapp.di.module

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton





@Module
class UtilsModule @Inject constructor(){

    private var context: Context? = null

    fun UtilsModule(context: Context) {
        this.context = context
    }
    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Singleton

    fun isOnLine(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
}
