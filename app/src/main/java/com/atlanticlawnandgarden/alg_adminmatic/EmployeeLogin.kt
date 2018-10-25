package com.atlanticlawnandgarden.alg_adminmatic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_employee_login.*
import org.json.JSONException
import org.json.JSONObject

class EmployeeLogin : AppCompatActivity() {
    var volleyRequest: RequestQueue? = null

    var user=""
    var pass=""
    var urlEmployeeLogin="https://www.atlanticlawnandgarden.com/cp/app/functions/other/logIn.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)
        volleyRequest = Volley.newRequestQueue(this)



        btn_login.setOnClickListener(({
            user= inp_user.text.toString()
            pass=inp_pass.text.toString()
            var urlConcat = urlEmployeeLogin+"?user="+user+"&pass="+pass
            getJsonObject(urlConcat)
        }))

    }

    fun getJsonObject(Url:String){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url,
                Response.Listener {
                    response: JSONObject ->
                    try{
                        val employeeLoggedIn = response.getString("loggedIn")
                        val empLevel = response.getInt("level")



                        //var propertyObj = employeesArray.getJSONObject(0)
                        val employeePreferences = SharedPreferences(this)
                        var employeeName = employeePreferences.getEmployeeName()
                        var loginStatus = employeePreferences.getLogStatus()
                        var employeeLevel = employeePreferences.getEmployeeLevel()


                        employeePreferences.setEmployeeName(employeeName)
                        employeePreferences.setLogStatus(loginStatus)
                        employeePreferences.setEmployeeLevel(employeeLevel)
                        Log.d("url", Url)
                        Log.d("login status", employeeLoggedIn)
                        Log.d("level", empLevel.toString())



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
