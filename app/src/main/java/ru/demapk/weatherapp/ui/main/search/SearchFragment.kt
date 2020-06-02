package ru.demapk.weatherapp.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_search.view.*
import ru.demapk.weatherapp.R

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    private lateinit var card: CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProvider(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        view.etSearch.doAfterTextChanged {
            searchViewModel.search(it.toString())
        }

        searchViewModel.found.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                view.cardData.isVisible = true

                view.textCityName.text = it.name
                view.textTemp.text =
                    getString(R.string.text_temperature, it.main?.temp?.toInt().toString())
                view.textInfo.text = it.weather?.firstOrNull()?.description

                view.btnAction.setOnClickListener { searchViewModel.buttonAction() }

            } else {
                view.cardData.isVisible = false
            }
        })

        searchViewModel.buttonState.observe(viewLifecycleOwner, Observer {
            if (it) {
                view.btnAction.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp)
            } else {
                view.btnAction.setImageResource(R.drawable.ic_playlist_add_black_24dp)
            }
        })

        searchViewModel.loading.observe(viewLifecycleOwner, Observer {
            view.loading.isVisible = it
        })

    }
}