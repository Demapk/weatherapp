package ru.demapk.weatherapp.ui.main.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_city.view.*
import ru.demapk.weatherapp.R
import ru.demapk.weatherapp.db.entity.relation.CityWithCurrentWeather

class CitiesAdapter : RecyclerView.Adapter<CitiesAdapter.CityHolder>() {

    private val data = arrayListOf<CityWithCurrentWeather>()

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {

        fun onItemClicked(data: CityWithCurrentWeather)

    }

    fun setListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    fun setList(data: List<CityWithCurrentWeather>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        return CityHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_city,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class CityHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textCityName = itemView.textCityName
        private val textTemp = itemView.textTemp

        fun bind(item: CityWithCurrentWeather) {

            textCityName.text = item.city.name
            val temp = item.currentWeather.firstOrNull()?.main?.temp?.toInt()
            temp?.let {
                textTemp.text = textTemp.context.getString(R.string.text_temperature, it.toString())
            }

            itemView.card_item.setOnClickListener {
                listener?.onItemClicked(item)
            }

            val desc = item.currentWeather.firstOrNull()?.weather?.description
            desc?.let{
                itemView.textDesc.text = it
            }



        }

    }


}
