package ru.demapk.weatherapp.ui.main.detailed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_forecast.view.*
import ru.demapk.weatherapp.R
import ru.demapk.weatherapp.db.entity.ForecastEntity

/**
 * Created by antonsarmatin
 * Date: 2020-04-23
 * Project: weatherapp
 */
class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastHolder>() {

    private val data = arrayListOf<ForecastEntity>()

    fun setData(data: List<ForecastEntity>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        return ForecastHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_forecast,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ForecastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: ForecastEntity) {

            item.textDate?.let {
                itemView.textTime.text = it
            }

            item.main?.let {
                val min = it.temp_min
                val max = it.temp_max

                itemView.textTemps. text = itemView.context.getString(R.string.text_temperatures, min.toInt().toString(), max.toInt().toString())
            }

            item.weather?.description?.let {
                itemView.textDesc.text = it
            }


        }


    }

}