package com.spdigital.weatherapp.views.cityweather

import android.content.Context
import android.provider.SearchRecentSuggestions
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spdigital.weatherapp.di.module.NetworkModule
import com.spdigital.weatherapp.di.module.RoomModule
import com.spdigital.weatherapp.di.module.UtilsModule
import com.spdigital.weatherapp.network.model.BaseWeather
import com.spdigital.weatherapp.roomapi.City
import com.spdigital.weatherapp.utils.Constants
import com.spdigital.weatherapp.utils.Status
import com.spdigital.weatherapp.views.home.CityNameSuggestionProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class CityWeatherActivityViewModel @Inject constructor() : ViewModel() {


    @Inject
    lateinit var networkModule: NetworkModule

    @Inject
    lateinit var utilsModule: UtilsModule

    @Inject
    lateinit var roomModule: RoomModule

    var weatherLiveData: MutableLiveData<BaseWeather>? = null

    var statusLiveData: MutableLiveData<Status>? = null

    init {
        if (weatherLiveData == null) {
            weatherLiveData = MutableLiveData()
        }

        if (statusLiveData == null) {
            statusLiveData = MutableLiveData()
        }
    }

    fun saveCurrentSearchQueryData(context: Context, query: String?) {
        if (query != null) {
            val suggestions = SearchRecentSuggestions(
                context,
                CityNameSuggestionProvider.AUTHORITY, CityNameSuggestionProvider.MODE
            )
            suggestions.saveRecentQuery(query, null)
        }
    }

    fun fetchWeatherdetail(context: Context, cityName: String) {
        utilsModule.UtilsModule(context)
        if (utilsModule.isOnLine()) {


            //call network api
            var retro = networkModule.provideWeatherRetrofitInterface()
            var weatherAPi = networkModule.provideWeatherApi(retro)

            var apiWeatherCall =
                weatherAPi.getWeatherDetail(
                    Constants.WEATHER_API_KEY,
                    cityName,
                    Constants.FORMATE,
                    Constants.NO_OF_DAY
                )

            apiWeatherCall.enqueue(object : Callback<BaseWeather> {

                override fun onResponse(
                    call: Call<BaseWeather>?,
                    response: Response<BaseWeather>?
                ) {
                    if (response?.body() != null ) {
                        weatherLiveData!!.value = response?.body()
                        insertCityName(context, cityName)
                    }
                }

                override fun onFailure(call: Call<BaseWeather>?, t: Throwable?) {
                    statusLiveData!!.value = Status.FAIL
                }
            })
        } else {
            // off line
            statusLiveData!!.value = Status.INTERNET_CONNECTION
        }
    }


    fun insertCityName(context: Context, cityName:String){
            roomModule.init(context)

        var roomDb = roomModule.getAppDatabase()
        var city = City(System.currentTimeMillis(), cityName)


        GlobalScope.launch {
            roomDb!!.daoBookAccess().insertTask(city)
        }

    }


}
