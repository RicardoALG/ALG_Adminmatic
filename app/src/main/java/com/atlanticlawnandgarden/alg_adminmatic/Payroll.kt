package com.atlanticlawnandgarden.alg_adminmatic

import android.app.TimePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate


class Payroll : AppCompatActivity() {

    var employeesList: ArrayList<String>? = null
    var volleyRequest: RequestQueue? = null
    var layoutManager: Spinner? = null
    var userid=""
    var userName=""

    var empID = "247"
    var date = LocalDate()

    var sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
    var saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)

    var weekStarts = "2018-10-14"
    var weekEnds = "2018-10-20"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payroll)

        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/employees.php"
        employeesList = ArrayList()
        volleyRequest = Volley.newRequestQueue(this)



        val shiftsVar = arrayOf("This Week", "Last Week")
        spinnerEmployee.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,shiftsVar)
        spinnerEmployee.setSelection(0,true)


        var extras = intent.extras
        if( extras != null){
            userid = extras.get("userID").toString()
            userName= extras.get("fullname").toString()
        }


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

        spinnerEmployeePayroll.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                spinnerEmployeePayroll.setSelection(4,false)
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

                //spinnerEmployeePayroll.setSelection(4,false)
                Toast.makeText(this@Payroll,"onIteM Selected",Toast.LENGTH_LONG).show()


            }

        }

        spinnerEmployee.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {


                sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
                saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)

                weekStarts=sunday.toString()
                weekEnds= saturday.toString()

                for(i in 0..6 ){
                }


            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


                //var today = DateTime(date)



                if (position==0){

                    sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
                    saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)

                    weekStarts=sunday.toString()
                    weekEnds=saturday.toString()
                    Log.d("Position==",position.toString())

                } else {
                    Log.d("Position==",position.toString())
                    sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(8)
                    saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(8)

                    weekStarts=sunday.toString()
                    weekEnds=saturday.toString()
                }


                GetJsonObj(userid, userName,this@Payroll,"http://www.atlanticlawnandgarden.com/cp/app/functions/get/shifts.php",empID,weekStarts,weekEnds)

                //getJsonObject(urlString)


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
        Log.d("user",spinnerEmployeePayroll.getItemAtPosition(4).toString())
        //spinnerEmployee.setSelection(4,false)


        return index
    }

    fun getEmployees(url: String){
        spinnerEmployeePayroll.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,employeesList)


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


                    val spinnerArrayAdapter = ArrayAdapter<String>(
                            this, R.layout.payroll_employee_row, employeesList
                    )
                    spinnerArrayAdapter.setDropDownViewResource(R.layout.payroll_employee_row)
                    spinnerEmployeePayroll.setAdapter(spinnerArrayAdapter)





                }
                val itemPosition = ArrayList(employeesList).indexOf(userName)
                Log.d("Employees position", itemPosition.toString())
                Log.d("Employees name", userName)
                spinnerEmployeePayroll.setSelection(itemPosition)
                Log.d("Employees Array", employeesList.toString())
                //spinnerEmployeePayroll.setSelection(getIndex(spinnerEmployeePayroll, "Dave Frank"))

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
