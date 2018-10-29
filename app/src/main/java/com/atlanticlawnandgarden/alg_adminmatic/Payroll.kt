package com.atlanticlawnandgarden.alg_adminmatic

import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_payroll.*
import com.google.android.gms.dynamic.SupportFragmentWrapper
import org.joda.time.DateTimeConstants
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class Payroll : AppCompatActivity() {

    var employeesList: ArrayList<String>? = null
    var volleyRequest: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payroll)

        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/employees.php"
        employeesList = ArrayList()
        employeesList!!.add(0,"test")
        volleyRequest = Volley.newRequestQueue(this)


        getEmployees(urlString)

        //spinnerEmployee.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,employeesList)
        //spinnerEmployee.setSelection(0,true)




        pr_start.setOnClickListener {

            val cal= Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE,minute)
                pr_start.text= SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@Payroll, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            finish()
        }))




        pr_end.setOnClickListener {

            val cal= Calendar.getInstance()

            val timeSetListener = TimePickerDialog.OnTimeSetListener{timePicker: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE,minute)
                pr_end.text= SimpleDateFormat("HH:mm").format(cal.time)
            }

            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        spinnerEmployee.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerEmployee.setSelection(4,false)
                Toast.makeText(this@Payroll,"WWWW",Toast.LENGTH_LONG).show()


//                sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
//                saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)
//
//                weekStarts=sunday.toString()
//                weekEnds= saturday.toString()

                for(i in 0..6 ){
                }


            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                //spinnerEmployee.setSelection(4,false)
                Toast.makeText(this@Payroll,"Sardina",Toast.LENGTH_LONG).show()


            }

        }
    }

    private fun getIndex(spinner: Spinner, myString: String): Int {

        var index = 0

        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == myString) {
                index = i
            }
            Log.d("spinner count", spinner.count.toString())
            Log.d("index in FOR", index.toString())
        }
        Log.d("user",spinnerEmployee.getItemAtPosition(4).toString())
        //spinnerEmployee.setSelection(4,false)


        return index
    }

    fun getEmployees(url: String){
        spinnerEmployee.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,employeesList)
        //spinnerEmployee.setSelection(0,true)

        val employeeRequest = JsonObjectRequest(Request.Method.GET,
                url, Response.Listener {
            response: JSONObject ->
            try{
                val resultArray = response.getJSONArray("employees")
                var empNum = resultArray.length()

                //employeesNum.text = empNum.toString()+" Active Employees"

                for(i in 0..resultArray.length() -1){
                    var employeeObj = resultArray.getJSONObject(i)


                    var employeeName = employeeObj.getString("name")


                    employeesList!!.add(employeeName)


                }
                Log.d("Employees Array", employeesList.toString())
                spinnerEmployee.setSelection(getIndex(spinnerEmployee, "Dave Frank"))

                //spinnerEmployee.setSelection(2,true)

                //employeeAdapter!!.notifyDataSetChanged()

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
