package com.asj.weather.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.asj.weather.R
import com.asj.weather.model.WeatherModel
import com.asj.weather.util.Utility
import kotlinx.android.synthetic.main.weather_row.view.*

class ForeCastAdapter(private val mContext: Context, private var alForeCastDay: ArrayList<WeatherModel.ForeCastDay>) : RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, intViewType: Int): ForeCastViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ForeCastViewHolder(layoutInflater.inflate(R.layout.weather_row, parent, false))
    }

    override fun onBindViewHolder(viewHolder: ForeCastViewHolder, position: Int) {
        val foreCastDay = alForeCastDay[position]

        if (foreCastDay != null) {

            viewHolder.collectTravelCash.tvDay.text = Utility.getDayName(foreCastDay.date)

            if (foreCastDay.dayOfForeCastDay != null) {
                viewHolder.collectTravelCash.tvTemp.text = foreCastDay.dayOfForeCastDay!!.avgTempC.toString() + " C"
            }
        }
    }

    override fun getItemCount(): Int = alForeCastDay.size

    class ForeCastViewHolder(val collectTravelCash: View) : RecyclerView.ViewHolder(collectTravelCash)
}
