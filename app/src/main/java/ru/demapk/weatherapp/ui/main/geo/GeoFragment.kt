package ru.demapk.weatherapp.ui.main.geo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_geo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.demapk.weatherapp.R

class GeoFragment : Fragment() {

    private lateinit var geoViewModel: GeoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        geoViewModel =
            ViewModelProvider(this).get(GeoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_geo, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geoViewModel.found.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                view.cardData.isVisible = true

                view.textCityName.text = it.name
                view.textTemp.text =
                    getString(R.string.text_temperature, it.main?.temp?.toInt().toString())
                view.textInfo.text = it.weather?.firstOrNull()?.description

                view.btnAction.setOnClickListener { geoViewModel.buttonAction() }

            } else {
                view.cardData.isVisible = false
            }
        })

        geoViewModel.buttonState.observe(viewLifecycleOwner, Observer {
            if (it) {
                view.btnAction.setImageResource(R.drawable.ic_remove_circle_outline_black_24dp)
            } else {
                view.btnAction.setImageResource(R.drawable.ic_playlist_add_black_24dp)
            }
        })

        geoViewModel.loading.observe(viewLifecycleOwner, Observer {
            view.loading.isVisible = it
        })

        geoViewModel.lastGeo.observe(viewLifecycleOwner, Observer {

            view.textLocation.text =
                getString(R.string.text_location, it.latitude.toString(), it.longitude.toString())

        })

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        } else {
            getLocation()
        }

    }


    private fun getLocation() {

        val singleRequest by lazy {
            LocationRequest().apply {
                numUpdates = 1
                maxWaitTime = 3000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
        }

        val locationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                locationClient.removeLocationUpdates(this)
                result?.let { locationResult ->
                    val location = locationResult.lastLocation
                    location?.let {
                        geoViewModel.fetchWithLocation(it)
                    }
                }
            }
        }

        try {
            CoroutineScope(Dispatchers.Main).launch {
                locationClient.requestLocationUpdates(
                    singleRequest,
                    locationCallback,
                    Looper.myLooper()
                )
            }
        } catch (e: Exception) {
            Log.e("locationSource", "Exception: ${e.message}")
        }

    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            112
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 112 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation()
        }

    }
}