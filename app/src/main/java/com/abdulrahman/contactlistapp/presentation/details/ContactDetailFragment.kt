package com.abdulrahman.contactlistapp.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.abdulrahman.contactlistapp.R
import com.abdulrahman.contactlistapp.core.DateUtils
import com.abdulrahman.contactlistapp.core.MONTH_DAY_YEAR_SLASH_FORMAT
import com.abdulrahman.contactlistapp.databinding.FragmentItemDetailBinding
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.MarkerOptions


class ContactDetailFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    val viewModel: ContactsDetailsViewModel by inject()
    var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                viewModel.getUser(it.getString(ARG_ITEM_ID)!!)
            }
        }
        viewModel.userFlow
            .filterNotNull()
            .onEach {
                _binding?.contactName?.text = it.name
                _binding?.mobile?.text = it.phone
                _binding?.memberSince?.text = DateUtils.formatDate(it.registered,SERVICE_DATE_FORMAT,MONTH_DAY_YEAR_SLASH_FORMAT)
                _binding?.email?.text = it.email
                _binding?.dob?.text = DateUtils.formatDate(it.dob,SERVICE_DATE_FORMAT,MONTH_DAY_YEAR_SLASH_FORMAT)
                _binding?.address?.text =
                    "${it.location.state}  ${it.location.city} , ${it.location.postcode} "
                _binding?.contactImage?.load(it.picture.large)
                initMap(LatLng(it.location.coordinates.latitude, it.location.coordinates.longitude))
            }.launchIn(lifecycleScope)

        _binding?.backIcon?.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initMap(latLng: LatLng) {
        val markerOptions = MarkerOptions()
        markerOptions.position(latLng)
        markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        googleMap?.addMarker(markerOptions)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val mapView = childFragmentManager.findFragmentById(R.id.mapView) as? SupportMapFragment
        mapView?.getMapAsync(this)
        return binding.root
    }


    companion object {
        const val ARG_ITEM_ID = "item_id"
        const val SERVICE_DATE_FORMAT="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }
}