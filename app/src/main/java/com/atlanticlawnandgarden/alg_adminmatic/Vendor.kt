package com.atlanticlawnandgarden.alg_adminmatic

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_vendor.*
import org.json.JSONException
import org.json.JSONObject

class Vendor : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {

    private lateinit var mMap: GoogleMap

    var paths: ArrayList<String> =ArrayList()
    var customers: ArrayList<String> =ArrayList()
    var likes: ArrayList<String> =ArrayList()
    var descriptions: ArrayList<String> =ArrayList()

    var volleyRequest: RequestQueue? = null
    val vendorURL = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/vendor.php?id="
    var id: String? = null
    var picURLprefix = "https://www.atlanticlawnandgarden.com/uploads/general/"

    var customRecyclerAdapter:CustomRecyclerAdapterGal?=null
    var imageModel:ArrayList<ImageModel> = ArrayList()
    var picName = ""
    var fullname =""
    var empName = ""
    var user = ""
    var lat: Double=41.4959713
    var lng: Double=-71.3767971

    override fun onMarkerClick(p0: Marker?)=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor)



        volleyRequest = Volley.newRequestQueue(this)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@Vendor, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@Vendor, VendorList::class.java)
            startActivity(clickintent)
        }))


        var extras = intent.extras

        if( extras != null){
            id = extras.get("ID").toString()
        }


        getJsonObject(vendorURL+id)
    }



    fun getJsonObject(Url:String){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url,
                Response.Listener {
                    response: JSONObject ->
                    try{
                        val vendorArray = response.getJSONObject("vendor")
                        val contactsArray = vendorArray.getJSONArray("contacts")


                        var propertyObj = vendorArray
                        var balance = "0.00"
                        var mainAddress: String? = "No Address Found"
                        var phone: String? = "No Phone Found"
                        var website: String? = "No Website Found"



                        Log.d("vendor",vendorArray.toString())





                        //var id = propertyObj.getString("ID")
                        var name = propertyObj.getString("name")
                        fullname = name
                        balance = propertyObj.getString("balance")
                        phone = propertyObj.getString("mainPhone")
                        website = propertyObj.getString("website")
                        mainAddress = propertyObj.getString("mainAddr")
                        var lngCheck=propertyObj.getString("lng")
                        if(lngCheck.isNotBlank()) lng=lngCheck.toDouble()
                        var latCheck= propertyObj.getString("lat")
                        if(latCheck.isNotBlank()) lat = latCheck.toDouble()
                        empName = mainAddress

                        val mapFragment = supportFragmentManager
                                .findFragmentById(R.id.map) as SupportMapFragment
                        mapFragment.getMapAsync(this)

                        var cPhone = phone.toString()

                        var cleanPhone = Regex("[^A-Za-z0-9 ]")
                        cPhone = cleanPhone.replace(cPhone,"")

                        vendorName.text = name
                        if(balance.isNullOrBlank() || website == "null") else txt_balance.text=balance
                        if(mainAddress.isNullOrBlank() || website == "null") else txt_address.text= mainAddress
                        if(website.isNullOrBlank() || website == "null") else txt_web.text = website
                        if(phone.isNullOrBlank() || website == "null") else txt_phone.text = phone



                        layout_btn_01.setOnClickListener {

                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cPhone))
                            startActivity(intent)
                        }



                        layout_btn_02.setOnClickListener {
                            val openURL = Intent(android.content.Intent.ACTION_VIEW)
                            openURL.data = Uri.parse("http://"+website)



                            try {

                                if(website.isNotBlank())startActivity(openURL)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                                Log.d("Email error:", e.toString())
                            }

                        }

                        layout_btn_03.setOnClickListener {

                            var clickintent = Intent(this@Vendor, MapsActivityAll::class.java)
                            clickintent.putExtra("mLat",lat)
                            clickintent.putExtra("mLon",lng)
                            clickintent.putExtra("customerName",name)


                            try {

                                startActivity(clickintent)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                                Log.d("Launch Map Error", e.toString())
                            }

                        }

                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    error: VolleyError? ->
                    try {
                        Log.d("Error",error.toString())
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                })
        volleyRequest!!.add(jsonObjectReq)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMarkerClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = true
        // Add a marker in Atlantic Lawn and Garden and move the camera
        val alg = LatLng(lat, lng)
        mMap.addMarker(MarkerOptions().position(alg).title("Atlantic Lawn and Garden"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(alg))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(alg, 17.05f))

    }
}
