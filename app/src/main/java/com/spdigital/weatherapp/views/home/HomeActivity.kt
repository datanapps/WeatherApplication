package com.spdigital.weatherapp.views.home

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.spdigital.weatherapp.R
import com.spdigital.weatherapp.roomapi.City
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity() {

    @Inject
    lateinit var homeActivityViewModel: HomeActivityViewModel

    var booksAdapter = CityNameAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

     //set tool bar
     setSupportActionBar(toolbarMainActivity)
     // city name list
     prepareRecycleView()

    // save default country list
        homeActivityViewModel.saveDefaultCityName(this)
    }



    fun prepareRecycleView() {

        recycleViewCity.itemAnimator = DefaultItemAnimator()

        recycleViewCity.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        recycleViewCity.adapter = booksAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(componentName))
        return true
    }


    override fun onResume() {
        super.onResume()

        // update list at resume
        updateCityNameList()
    }


    fun updateCityNameList() {
        homeActivityViewModel.getUpdatedCityNameList(this, this)


        homeActivityViewModel.cityNameListData?.observe(this,
            Observer<List<City>> { cityList ->
                if (cityList != null) {
                    booksAdapter.updateBookList(cityList)
                }
            })
    }
}
