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
import kotlinx.android.synthetic.main.activity_customer.*
import kotlinx.android.synthetic.main.emp_img.*
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
    }

    fun getJsonObject(Url:String){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url,
                Response.Listener {
                    response: JSONObject ->
                    try{
                        val customerArray = response.getJSONObject("customer")



                        var propertyObj = customerArray

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
//                        var phone = propertyObj.getString("phone")
//                        var email = propertyObj.getString("email")
//
//                        var cPhone = phone
//
//                        var cleanPhone = Regex("[^A-Za-z0-9 ]")
//                        cPhone = cleanPhone.replace(cPhone,"")

                        ///////POPULATE ACTIVITY

                        txt_customer.text = name
//                        txt_phone.text = phone
//                        txt_email.text = email


//                        Picasso.get()
//                                .load(picURLprefix+pic)
//                                .placeholder(R.drawable.user_placeholder)
//                                .error(R.drawable.user_placeholder)
//                                .into(employeePic)

                        layout_phone_btn.setOnClickListener {

//                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cPhone))
//                            startActivity(intent)
                        }



                        layout_email_btn.setOnClickListener {

                            val intent = Intent(Intent.ACTION_SENDTO)
                            intent.type = "message/rfc822"
//                            intent.putExtra(Intent.EXTRA_EMAIL, email)
//                            intent.data = Uri.parse("mailto:$email")
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
