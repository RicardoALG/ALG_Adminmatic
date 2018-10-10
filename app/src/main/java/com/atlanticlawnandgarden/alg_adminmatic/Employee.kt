package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_employee.*
import org.json.JSONException
import org.json.JSONObject

class Employee : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    val employeeURL = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/employeeInfo.php?empID="
    var id = "229"
    var picURLprefix = "https://www.atlanticlawnandgarden.com/uploads/general/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        volleyRequest = Volley.newRequestQueue(this)

        var extras = intent.extras

        if( extras != null){
            id = extras.get("ID").toString()
        }

        getJsonObject(employeeURL+id)



        btn_home.setOnClickListener(({
            var clickintent = Intent(this@Employee, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@Employee, EmployeesList::class.java)
            startActivity(clickintent)
        }))


    }

    fun getJsonObject(Url:String){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url,
                Response.Listener {
                    response: JSONObject ->
                    try{
                        val employeesArray = response.getJSONArray("employees")



                            var propertyObj = employeesArray.getJSONObject(0)
                        Log.d("Camote en papas",propertyObj.toString())
                            var id = propertyObj.getString("ID")
                            var name = propertyObj.getString("name")
                            var lname = propertyObj.getString("lname")
                            var fname = propertyObj.getString("fname")
                            var userName = propertyObj.getString("username")
                            var pic = propertyObj.getString("pic")
                            var phone = propertyObj.getString("phone")
                            var email = propertyObj.getString("email")
                        Log.d("Queso Pladota",email)
                        txt_email.text=email

                        ///////POPULATE ACTIVITY

                        txt_employee_name.text = name
                        txt_phone.text = phone
                        txt_email.text = email


                            Picasso.get()
                                    .load(picURLprefix+pic)
                                    .placeholder(R.drawable.user_placeholder)
                                    .error(R.drawable.user_placeholder)
                                    .into(employeePic)




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
