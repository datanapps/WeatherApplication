

package com.spdigital.weatherapp.views.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spdigital.weatherapp.R
import com.spdigital.weatherapp.roomapi.City
import kotlinx.android.synthetic.main.layout_city_list.view.*

class CityNameAdapter : RecyclerView.Adapter<CityNameAdapter.CityNameViewHolder>() {
    private var cityNameList: List<City>

    init {
        cityNameList = arrayListOf<City>()
    }

    fun updateBookList(moviesList: List<City>){
        this.cityNameList = moviesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityNameViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_city_list, parent, false)
        return CityNameViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: CityNameViewHolder, position: Int) {
        holder.bindView(cityNameList[position], position+1)
    }

    override fun getItemCount(): Int {
        return cityNameList.size
    }


    inner class CityNameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       fun bindView (city: City, position:Int) {
           itemView.layoutCityName.text = city.name
       }


    }



}
