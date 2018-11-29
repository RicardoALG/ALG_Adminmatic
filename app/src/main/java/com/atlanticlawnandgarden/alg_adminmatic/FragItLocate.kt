package com.atlanticlawnandgarden.alg_adminmatic

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class FragItLocate : Fragment(), OnMapReadyCallback{
    private lateinit var mMap: GoogleMap




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        var mapFragment = childFragmentManager
                .findFragmentById(R.id.map2) as SupportMapFragment?

        if (mapFragment == null) {

            // WARNING!!! This next 2 lines are causing the app to slow and crash

            /*mapFragment = SupportMapFragment.newInstance()
            childFragmentManager.beginTransaction().replace(R.id.map2, mapFragment).commit()*/

        }

        mapFragment?.getMapAsync(this)

        return inflater.inflate(R.layout.frag_it_locate, container, false)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //mMap.setOnMarkerClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true
        // Add a marker in Atlantic Lawn and Garden and move the camera
        val alg = LatLng(41.4958611, -71.3768593)
        mMap.addMarker(MarkerOptions().position(alg).title("Atlantic Lawn and Garden"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(alg))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alg, 17.05f))


    }
}