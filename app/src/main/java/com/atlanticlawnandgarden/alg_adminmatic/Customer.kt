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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_customer.*
import org.json.JSONException
import org.json.JSONObject

class Customer : AppCompatActivity() {

    var paths: ArrayList<String> =ArrayList()
    var customers: ArrayList<String> =ArrayList()
    var likes: ArrayList<String> =ArrayList()
    var descriptions: ArrayList<String> =ArrayList()

    var volleyRequest: RequestQueue? = null
    val customerURL = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/customer.php?ID="
    var id = "229"
    var picURLprefix = "https://www.atlanticlawnandgarden.com/uploads/general/"

    ////////////GALLERY STUFF

    val REQUEST_CODE = 1

    var customRecyclerAdapter:CustomRecyclerAdapterGal?=null
    var imageModel:ArrayList<ImageModel> = ArrayList()
    var picName = ""
    var fullname =""
    var empName = ""
    var user = ""

    ////////////ExpandibleListView constants
    val header: MutableList<String> = ArrayList()
    val body: MutableList<MutableList<String>> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer)

        volleyRequest = Volley.newRequestQueue(this)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@Customer, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@Customer, CustomersList::class.java)
            startActivity(clickintent)
        }))


        var extras = intent.extras

        if( extras != null){
            id = extras.get("ID").toString()
        }


        getJsonObject(customerURL+id)


        ///////////////ExpandibleListView
        title = "MORE INFO"
        val season1: MutableList<String> = ArrayList()
        season1.add("Mike Abbood (middle St. Account)J...")
        season1.add("5 Middle St. Jamestown, RI")
        season1.add("4230591")
        season1.add("struf13@cox.net")
        header.add("MORE INFO")
        body.add(season1)
        expandableListView.setAdapter(ExpandableListAdapter(this,expandableListView, header, body))
    }

    fun getJsonObject(Url:String){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url,
                Response.Listener {
                    response: JSONObject ->
                    try{
                        val customerArray = response.getJSONObject("customer")
                        val contactsArray = customerArray.getJSONArray("contacts")


                        var propertyObj = customerArray
                        var contactAddress: String? = "No Address Found"
                        var contactPhone: String? = "No Phone Found"
                        var contactEmail: String? = "No Email Found"
                        var contactLat: String? = null
                        var contactLon: String? = null

                        for(i in 0..contactsArray.length() -1) {

                            Log.d("number of contact", contactsArray.length().toString())

                            when(contactsArray.getJSONObject(i).getString("type")){
                                "0" -> Log.d("type is", "none")
                                "1" -> {
                                    Log.d("FOUND", "Phone")
                                    contactPhone = contactsArray.getJSONObject(i).getString("value")
                                }
                                "2" -> {
                                    Log.d("FOUND", "Email")
                                    contactEmail = contactsArray.getJSONObject(i).getString("value")
                                }
                                "3" -> Log.d("type is", "Billing Address")
                                "4" -> {
                                    Log.d("FOUND", "Address")
                                    contactAddress = contactsArray.getJSONObject(i).getString("fullAddress")
                                    contactLat = contactsArray.getJSONObject(i).getString("lat")
                                    contactLon = contactsArray.getJSONObject(i).getString("lng")
                                }
                                "5" -> Log.d("type is", "Website")
                                "6" -> Log.d("type is", "Alt Contact")
                                "7" -> Log.d("type is", "Fax")
                                "8" -> Log.d("type is", "Alt Phone")
                                "9" -> Log.d("type is", "Alt Email")
                                "10" -> Log.d("type is", "Mobile")
                                "11" -> Log.d("type is", "Alt Mobile")
                                "12" -> Log.d("type is", "Home Phone")
                                "13" -> Log.d("type is", "Alt Email 2")
                                "14" -> Log.d("type is", "Invoice Address")
                                "15"-> Log.d("type is", "Alt Job Site")
                                //contactPhone = contactsArray.getJSONObject(i).getString("Main Phone")
                            }


                        }



                        Log.d("customer",customerArray.toString())





                        //var id = propertyObj.getString("ID")
                        var name = propertyObj.getString("name")
                        fullname = name
                        var lname = propertyObj.getString("lname")
                        var fname = propertyObj.getString("fname")
                        empName = fname
//                        var userName = propertyObj.getString("username")
//                        user = userName
//                        var pic = propertyObj.getString("pic")
//                        picName = pic

//                        var cPhone = phone
//
//                        var cleanPhone = Regex("[^A-Za-z0-9 ]")
//                        cPhone = cleanPhone.replace(cPhone,"")

                        ///////POPULATE ACTIVITY

                        var cPhone = contactPhone.toString()

                        var cleanPhone = Regex("[^A-Za-z0-9 ]")
                        cPhone = cleanPhone.replace(cPhone,"")

                        customerName.text = name
                        txt_address.text = contactAddress
                        txt_email.text = contactEmail
                        txt_phone.text = contactPhone


//                        Picasso.get()
//                                .load(picURLprefix+pic)
//                                .placeholder(R.drawable.user_placeholder)
//                                .error(R.drawable.user_placeholder)
//                                .into(employeePic)

                        layout_btn_01.setOnClickListener {

                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cPhone))
                            startActivity(intent)
                        }



                        layout_btn_02.setOnClickListener {

                            val intent = Intent(Intent.ACTION_SENDTO)
                            intent.type = "message/rfc822"
                            intent.putExtra(Intent.EXTRA_EMAIL, contactEmail)
                            intent.data = Uri.parse("mailto:$contactEmail")
                            intent.putExtra(Intent.EXTRA_SUBJECT, "From AdminMatic")
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.addFlags(Intent.FLAG_FROM_BACKGROUND)
                            try {

                                startActivity(intent)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                                Log.d("Email error:", e.toString())
                            }

                        }

                        layout_btn_03.setOnClickListener {

                            var clickintent = Intent(this@Customer, MapsActivityAll::class.java)
                            clickintent.putExtra("mLat",contactLat)
                            clickintent.putExtra("mLon",contactLon)
                            clickintent.putExtra("customerName",name)

                            try {

                                startActivity(clickintent)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                                Log.d("Launch Map Error", e.toString())
                            }

//                            val gmmIntentUri = Uri.parse(contactLat+","+contactLon)
//                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                            mapIntent.setPackage("com.google.android.apps.maps")
//                            if (mapIntent.resolveActivity(packageManager) != null) {
//                                startActivity(mapIntent)
//                            }
                        }

//                        btnDepartments.setOnClickListener {
//                            var intent = Intent(this, DepartmentCrewList::class.java)
//                            intent.putExtra("empID",id)
//                            intent.putExtra("crewView",0)
//
//                            startActivity(intent)
//                        }
//
//                        btnCrews.setOnClickListener {
//                            var intent = Intent(this, DepartmentCrewList::class.java)
//                            intent.putExtra("empID",id)
//                            intent.putExtra("crewView",1)
//
//                            startActivity(intent)
//                        }
//
//                        btnShifts.setOnClickListener {
//                            var intent = Intent(this, ShiftsList::class.java)
//                            intent.putExtra("empID",id)
//                            intent.putExtra("fName",fname)
//
//                            startActivity(intent)
//                        }


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
