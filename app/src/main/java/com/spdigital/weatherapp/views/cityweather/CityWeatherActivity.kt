package com.spdigital.weatherapp.views.cityweather


import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.spdigital.weatherapp.R
import com.spdigital.weatherapp.network.model.BaseWeather
import com.spdigital.weatherapp.utils.SnakBar
import com.spdigital.weatherapp.utils.Status
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_city_weather.*
import javax.inject.Inject

class CityWeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var cityWeatherActivityViewModel: CityWeatherActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_weather)

        //set default toolbar
        setSupportActionBar(toolBarSearchResult)

        // handle new imtent
        handleIntent(intent)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            val query = intent.getStringExtra(SearchManager.QUERY)
           cityWeatherActivityViewModel.saveCurrentSearchQueryData(this, query)
            toolBarSearchResult.title = query
            setSupportActionBar(toolBarSearchResult)
            cityWeatherActivityViewModel.fetchWeatherdetail(this, query)
        }
    }


    override fun onStart() {
        super.onStart()
        callWeatherApiAndUpdateUI()
    }

    private fun callWeatherApiAndUpdateUI() {

        cityWeatherProgressBar.visibility= View.VISIBLE

        // weather live data
        cityWeatherActivityViewModel.weatherLiveData?.observe(this,
            Observer<BaseWeather> { baseWeather ->
                cityWeatherProgressBar.visibility= View.GONE
                if (baseWeather != null && baseWeather.data.currentCondition!=null) {

                    Glide.with(this)
                        .load(baseWeather.data.currentCondition[0].weatherIconUrl[0].value)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(cityWeatherImage);
                    cityWeatherHumidity.text = getString(R.string.humidity, baseWeather.data.currentCondition[0].humidity+"%")
                    cityWeatherDescription.text = getString(R.string.weather_des, baseWeather.data.currentCondition[0].weatherDesc[0].value)
                    cityWeatherTemp.text = getString(R.string.weather_temp, baseWeather.data.currentCondition[0].tempC+" \u2103")

                } else  if(baseWeather.data.error !=null){
                   SnakBar.show(this, baseWeather.data.error[0].msg)
                }
                else{
                    SnakBar.show(this, getString(R.string.msg_something_went_wrong))
                }
            })


        // status od actions
        cityWeatherActivityViewModel.statusLiveData?.observe(this,
            Observer<Status> { status ->
                cityWeatherProgressBar.visibility= View.GONE

                when(status){
                    Status.INTERNET_CONNECTION->    SnakBar.show(this, getString(R.string.msg_no_internet_network))
                    Status.SERVER_ERROR->    SnakBar.show(this, getString(R.string.msg_server_error))
                    Status.FAIL->    SnakBar.show(this, getString(R.string.msg_something_went_wrong))
                    else-> SnakBar.show(this, getString(R.string.msg_unknown))
                }
            })

    }

}