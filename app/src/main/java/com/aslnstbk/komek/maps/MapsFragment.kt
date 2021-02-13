package com.aslnstbk.komek.maps

import androidx.fragment.app.Fragment
import com.aslnstbk.komek.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback {

    override fun onMapReady(googleMap: GoogleMap?) {
        val sydney = LatLng(-34.0, 151.0)

        googleMap?.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}