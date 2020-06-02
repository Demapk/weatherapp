package ru.demapk.weatherapp.ui.main.detailed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_detailed.view.*
import kotlinx.android.synthetic.main.fragment_detailed.view.btnAction
import kotlinx.android.synthetic.main.fragment_detailed.view.cardData
import kotlinx.android.synthetic.main.fragment_detailed.view.textCityName
import kotlinx.android.synthetic.main.fragment_detailed.view.textInfo
import kotlinx.android.synthetic.main.fragment_detailed.view.textTemp
import kotlinx.android.synthetic.main.fragment_search.view.*
import ru.demapk.weatherapp.R
import ru.demapk.weatherapp.ui.main.MainActivity

/**
 * Project: weatherapp
 */
class DetailedFragment : Fragment() {

    private lateinit var detailedViewModel: DetailedViewModel

    private lateinit var adapter: ForecastAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailedViewModel =
            ViewModelProvider(this).get(DetailedViewModel::class.java)

        arguments?.let { args ->
            if (args.containsKey(KEY_CITY_ID)) {
                detailedViewModel.init(args.getLong(KEY_CITY_ID, -1))
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_detailed, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ForecastAdapter()
        layoutManager = LinearLayoutManager(requireContext())

        view.recyclerview.apply {
            adapter = this@DetailedFragment.adapter
            layoutManager = this@DetailedFragment.layoutManager
        }


        detailedViewModel.weather.observe(viewLifecycleOwner, Observer {

            adapter.setData(it.forecast)

            view.cardData.isVisible = true

            view.textCityName.text = it.city.name
            view.textTemp.text =
                getString(R.string.text_temperature, it.currentWeather[0].main?.temp?.toInt().toString())
            view.textInfo.text = it.currentWeather[0].weather?.description

            view.btnAction.setOnClickListener { detailedViewModel.buttonAction() }

        })

        detailedViewModel.buttonState.observe(viewLifecycleOwner, Observer {
            view.btnAction.isVisible = it
            view.btnAction.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp)
        })



    }

    override fun onDestroyView() {
        (activity as MainActivity).showNavigation()
        super.onDestroyView()
    }

    companion object {

        const val KEY_CITY_ID = "nav_city_id"

    }

}