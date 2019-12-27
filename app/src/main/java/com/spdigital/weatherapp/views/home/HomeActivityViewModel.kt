package com.spdigital.weatherapp.views.home

import android.content.Context
import android.provider.SearchRecentSuggestions
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spdigital.weatherapp.di.module.RoomModule
import com.spdigital.weatherapp.roomapi.City
import com.spdigital.weatherapp.utils.Constants
import javax.inject.Inject


class HomeActivityViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var roomModule: RoomModule

    var cityNameListData: MutableLiveData<List<City>>? = null




    init {
        if (cityNameListData == null) {
            cityNameListData = MutableLiveData()
        }

    }

    fun saveDefaultCityName(context: Context) {

        Constants.DEFAULT_COUNTRY_LIST.forEach{ element ->
            SearchRecentSuggestions(
                context,
                CityNameSuggestionProvider.AUTHORITY, CityNameSuggestionProvider.MODE
            ).saveRecentQuery(element, null)
        }

        }


    fun getUpdatedCityNameList(lifecycleOwner: LifecycleOwner, context: Context){
        roomModule.init(context)

        var roomDb = roomModule.getAppDatabase()

        roomDb!!.daoBookAccess().fetchCityNameList(Constants.CITY_LIST_LIMIT).observe(lifecycleOwner, androidx.lifecycle.Observer {
            cityNameListData!!.value = it
        })

    }

}
