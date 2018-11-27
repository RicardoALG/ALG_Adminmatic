package com.atlanticlawnandgarden.alg_adminmatic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivityAll : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    var mapLat: String? = null
    var mapLon:String? = null
    var mapName:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_all)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var extras = intent.extras

        if( extras != null){
            mapLat= extras.get("mLat").toString()
            mapLon= extras.get("mLon").toString()
            mapName= extras.get("customerName").toString()

        }

        // Add a marker in Sydney and move the camera
        val customerLocation = LatLng(mapLat!!.toDouble(), mapLon!!.toDouble())
        mMap.addMarker(MarkerOptions().position(customerLocation).title(mapName))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(customerLocation))

        mMap.getUiSettings().setZoomControlsEnabled(true)
        mMap.getUiSettings().isMapToolbarEnabled
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(customerLocation, 15.0f))

        Log.d("Maps","ActivityALL")


    }
}
