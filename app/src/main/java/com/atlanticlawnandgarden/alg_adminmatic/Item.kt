package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_item.*
import org.json.JSONException
import org.json.JSONObject

class Item : AppCompatActivity() {

    private var mSectionsPagerAdapter: Item.SectionsPagerAdapter? = null

    var paths: ArrayList<String> =ArrayList()
    var items: ArrayList<String> =ArrayList()
    var likes: ArrayList<String> =ArrayList()
    var descriptions: ArrayList<String> =ArrayList()

    var volleyRequest: RequestQueue? = null
    val itemURL = "https://www.atlanticlawnandgarden.com/cp/app/functions/zzTest/itemGET.php?itemID="
    var id: String? = null

    ////////////GALLERY STUFF

    val REQUEST_CODE = 1

    var customRecyclerAdapter:CustomRecyclerAdapterGal?=null
    var itemName = ""
    var itemID =""
    var type = ""
    var price = ""
    var unit = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        volleyRequest = Volley.newRequestQueue(this)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@Item, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@Item, ItemsList::class.java)
            startActivity(clickintent)
        }))

        var extras = intent.extras

        if( extras != null){
            id = extras.get("ID").toString()
            type = extras.get("TYPE").toString()
        }
        Log.d("url:","======================="+itemURL+id)
        getJsonObject(itemURL+id)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            when(position){
                0 -> {
                    return FragItLocate()
                }
                1 -> {
                    return FragItVendor()
                }
                2 -> {
                    return FragItWorkOrder()
                }
                else -> return null
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when(position){
                0 -> return "LOCATE"
                1 -> return  "VENDOR"
                2 -> return  "WORK ORDER"

            }
            return null
        }
    }



    fun getJsonObject(Url:String?){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url,
                Response.Listener {
                    response: JSONObject ->
                    try{
                        val itemObject = response.getJSONObject("item")
                        val vendorsArray = itemObject.getJSONArray("vendors")
                        var numberOfVendors = vendorsArray.length()


                        //// Array to store Lat & Lng
                        var vendorCoords = arrayOf<Array<String>>()




                        Log.d("itemArray",itemObject.toString())
                        Log.d("vendorsArray",vendorsArray.toString())

                        Log.d("number of vendors ==",numberOfVendors.toString())

                        for(i in 0..vendorsArray.length() -1) {
                            val coordsArray = vendorsArray.getJSONObject(i).getJSONArray("address")

                            var addLat:String =coordsArray.getJSONObject(0).getString("lat")
                            var addLng: String =coordsArray.getJSONObject(0).getString("lng")

                            var array = arrayOf<String>()

                            for (j in 0..1){

                               if(j==0) array += addLat else array+=addLng
                            }
                            vendorCoords+=array

                        }

                        for (array in vendorCoords) {
                            for (value in array) {
                                print("$value ")
                            }
                            println()
                        }




                        var propertyObj = itemObject

                        var itemName: String? = "No Address Found"
                        var itemDescription: String? = itemObject.getString("description")
                        var itemType: String? = "No Email Found"
                        var itemPrice: String? = null
                        var itemTax: String? = itemObject.getString("tax")
                        var itemUnit: String = itemObject.getString(("unitName"))




                        txt_item.text = itemObject.getString("item")
                        txt_description.text = if(itemDescription=="") "NA" else itemDescription
                        txt_type.text = type
                        txt_price.text = "$"+itemObject.getString("price")+"/"+if(itemUnit=="Not Assigned" || itemUnit=="") "NA" else itemUnit
                        txt_tax.text =if(itemTax=="0") "Non Taxable" else "Taxable"



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

}
