package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.net.Uri
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
import java.lang.Exception

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

                            var cPhone = phone

                            var cleanPhone = Regex("[^A-Za-z0-9 ]")
                            cPhone = cleanPhone.replace(cPhone,"")

                        ///////POPULATE ACTIVITY

                        txt_employee_name.text = name
                        txt_phone.text = phone
                        txt_email.text = email


                            Picasso.get()
                                    .load(picURLprefix+pic)
                                    .placeholder(R.drawable.user_placeholder)
                                    .error(R.drawable.user_placeholder)
                                    .into(employeePic)

                        layout_phone_btn.setOnClickListener {

                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cPhone))
                            startActivity(intent)
                        }



                        layout_email_btn.setOnClickListener {

                            val intent = Intent(Intent.ACTION_SENDTO)
                            intent.type = "message/rfc822"
                            intent.putExtra(Intent.EXTRA_EMAIL, email)
                            intent.data = Uri.parse("mailto:$email")
                            intent.putExtra(Intent.EXTRA_SUBJECT, "From AdminMatic")
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.addFlags(Intent.FLAG_FROM_BACKGROUND)
                            try {

                                startActivity(intent)
                            } catch (e: android.content.ActivityNotFoundException) {
                                e.printStackTrace()
                                Log.d("Email error:", e.toString())
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


}
