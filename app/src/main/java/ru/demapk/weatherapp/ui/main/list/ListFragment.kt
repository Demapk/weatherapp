package ru.demapk.weatherapp.ui.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.view.*
import ru.demapk.weatherapp.R
import ru.demapk.weatherapp.db.entity.relation.CityWithCurrentWeather
import ru.demapk.weatherapp.ui.main.MainActivity
import ru.demapk.weatherapp.ui.main.detailed.DetailedFragment

class ListFragment : Fragment(), CitiesAdapter.OnItemClickListener {

    private lateinit var listViewModel: ListViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var listAdapter: CitiesAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel =
            ViewModelProvider(this).get(ListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_list, container, false)

        listViewModel.fetch()

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.recyclerview
        listAdapter = CitiesAdapter().apply { setListener(this@ListFragment) }
        layoutManager = LinearLayoutManager(requireContext())

        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = layoutManager

        listViewModel.citiesList.observe(viewLifecycleOwner, Observer {
            listAdapter.setList(it)
        })


        view.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.navigation_search)
        }

    }

    override fun onResume() {
        super.onResume()

        listViewModel.fetch()
    }



    override fun onItemClicked(data: CityWithCurrentWeather) {
        (activity as MainActivity).hideNavigation()
        findNavController().navigate(
            R.id.action_navigation_list_to_detailedFragment,
            bundleOf(DetailedFragment.KEY_CITY_ID to data.city.id)
        )
    }
}