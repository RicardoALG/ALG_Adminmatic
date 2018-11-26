package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import kotlinx.android.synthetic.main.activity_vendor_list.*

class VendorList : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var vendorsList: ArrayList<VendorCard>? = null
    var vendorAdapter: VendorListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@VendorList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/vendors.php"

        vendorsList = ArrayList<VendorCard>()
        volleyRequest = Volley.newRequestQueue(this)

        getVendor(urlString)

    }

    fun getVendor(url: String){
        val vendorRequest = JsonObjectRequest(Request.Method.GET,
                url, Response.Listener {
            response: JSONObject ->
            try{
                val resultArray = response.getJSONArray("vendors")
                var vendNum = resultArray.length()

                Log.d("vendors", vendNum.toString()+"mamitos======"+resultArray)

                vendorsNum.text = vendNum.toString()+" Active Vendors"

                for(i in 0..resultArray.length() -1){
                    var vendorObj = resultArray.getJSONObject(i)


                    var vendorname = vendorObj.getString("name")
                    //var address = vendorObj.getString("mainAddr")
                    //var customerPic = "https://www.atlanticlawnandgarden.com/uploads/general/"+customerObj.getString("pic")
                    var vendorD = vendorObj.getString("ID")

                    //Check if Info is being pulled from server
                    //Log.d("Result ===>", employeeName)


                    var vendorCard = VendorCard()
                    vendorCard.vendorname = vendorname
                    //vendorCard.address=address
                    //customerCard.thumbnail = customerPic
                    vendorCard.link = vendorD

                    vendorsList!!.add(vendorCard)

                    vendorAdapter = VendorListAdapter(vendorsList!!,this)
                    layoutManager = LinearLayoutManager(this)

                    //setup list

                    recyclerViewVendors.layoutManager = layoutManager
                    recyclerViewVendors.adapter = vendorAdapter
                }

                vendorAdapter!!.notifyDataSetChanged()

            } catch (e: Exception){

            }

        },
                Response.ErrorListener {
                    error: VolleyError ->
                    try {
                        Log.d("Error: ", error.toString())
                    } catch (e: JSONException){
                        e.printStackTrace()
                    }
                })


        volleyRequest!!.add(vendorRequest)
    }
}
