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
import kotlinx.android.synthetic.main.activity_employees_list.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class EmployeesList : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var employeesList: ArrayList<EmployeeCard>? = null
    var employeeAdapter: EmployeeListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@EmployeesList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@EmployeesList, MainMenu::class.java)
            startActivity(clickintent)
        }))

//        CustomerBtn.setOnClickListener(({
//            var clickintent = Intent(this@EmployeesList, Employee::class.java)
//            startActivity(clickintent)
//        }))

        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/employees.php"

        employeesList = ArrayList<EmployeeCard>()
        volleyRequest = Volley.newRequestQueue(this)

        getEmployee(urlString)
    }

    fun getEmployee(url: String){
        val employeeRequest = JsonObjectRequest(Request.Method.GET,
                url, Response.Listener {
            response: JSONObject ->
            try{
                val resultArray = response.getJSONArray("employees")
                var empNum = resultArray.length()

                employeesNum.text = empNum.toString()+" Active Employees"

                for(i in 0..resultArray.length() -1){
                    var employeeObj = resultArray.getJSONObject(i)


                    var employeeName = employeeObj.getString("name")
                    var employeePic = "https://www.atlanticlawnandgarden.com/uploads/general/"+employeeObj.getString("pic")
                    var employeeID = employeeObj.getString("ID")

                    //Check if Info is being pulled from server
                    //Log.d("Result ===>", employeeName)

                    var employeeCard = EmployeeCard()
                    employeeCard.username = employeeName
                    employeeCard.thumbnail = employeePic
                    employeeCard.link = employeeID

                    employeesList!!.add(employeeCard)

                    employeeAdapter = EmployeeListAdapter(employeesList!!,this)
                    layoutManager = LinearLayoutManager(this)

                    //setup list

                    recyclerViewEmployees.layoutManager = layoutManager
                    recyclerViewEmployees.adapter = employeeAdapter
                }

                employeeAdapter!!.notifyDataSetChanged()

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


        volleyRequest!!.add(employeeRequest)
    }
}
