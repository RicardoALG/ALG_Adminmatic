package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_employee_login.*
import org.json.JSONException
import org.json.JSONObject
import java.time.Duration

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

        btn_loginCheck.setOnClickListener(({
            val employeePreferences = SharedPreferences(this)
            var employeeID = employeePreferences.getEmployeeID()
            var employeeFName = employeePreferences.getEmployeeFName()
            var employeeLName = employeePreferences.getEmployeeLName()
            var loginStatus = employeePreferences.getLogStatus()
            var employeeLevel = employeePreferences.getEmployeeLevel()


            Log.d("SP ID", employeeID)
            Log.d("SP FName", employeeFName)
            Log.d("SP LName", employeeLName)
            Log.d("SP login status", loginStatus.toString())
            Log.d("SP level", employeeLevel.toString())
        }))

    }

    fun getJsonObject(Url:String){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url,
                Response.Listener {
                    response: JSONObject ->
                    try{
                        var empID = response.getString("id")
                        var empFname = response.getString("username")
                        var empLname = response.getString("userlastname")
                        var empLogIn = response.getString("loggedIn")
                        var empLevel = response.getString("level")

                        if(empLevel=="none"){
                            empLevel="0"
                        }




                        val employeePreferences = SharedPreferences(this)


                        employeePreferences.setEmployeeID(empID)
                        employeePreferences.setEmployeeFName(empFname)
                        employeePreferences.setEmployeeLName(empLname)
                        employeePreferences.setLogStatus(empLogIn.toBoolean())
                        employeePreferences.setEmployeeLevel(empLevel.toInt())

                        var employeeID = employeePreferences.getEmployeeID()
                        var employeeFName = employeePreferences.getEmployeeFName()
                        var employeeLName = employeePreferences.getEmployeeLName()
                        var loginStatus = employeePreferences.getLogStatus()
                        var employeeLevel = employeePreferences.getEmployeeLevel()
                        Log.d("SP ID", employeeID)
                        Log.d("SP FName", employeeFName)
                        Log.d("SP LName", employeeLName)
                        Log.d("SP login status", loginStatus.toString())
                        Log.d("SP level", employeeLevel.toString())

                        if(empLogIn=="true"){
                            Toast.makeText(this,"LOGIN SUCCESSFUL!",Toast.LENGTH_LONG).show()
                            var clickintent = Intent(this@EmployeeLogin, Employee::class.java)
                            startActivity(clickintent)
                        } else {
                            Toast.makeText(this,"LOG IN UNSUCCESSFUL. TRY AGAIN.",Toast.LENGTH_LONG).show()
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
