package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_vendor.*

class Vendor : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private lateinit var mMap: GoogleMap

    override fun onMarkerClick(p0: Marker?)=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@Vendor, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@Vendor, VendorList::class.java)
            startActivity(clickintent)
        }))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMarkerClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true
        // Add a marker in Atlantic Lawn and Garden and move the camera
        val alg = LatLng(41.4958611, -71.3768593)
        mMap.addMarker(MarkerOptions().position(alg).title("Atlantic Lawn and Garden"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(alg))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alg, 17.05f))


    }
}
