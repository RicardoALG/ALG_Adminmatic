package com.atlanticlawnandgarden.alg_adminmatic

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_payroll.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import org.joda.time.DateTimeConstants
import org.joda.time.LocalDate
import java.text.DecimalFormat


class Payroll : AppCompatActivity() {

    var employeesList: ArrayList<String>? = null
    var volleyRequest: RequestQueue? = null
    var layoutManager: Spinner? = null
    var userid=""
    var userName=""

    var empID = ""
    var date = LocalDate()

    var sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
    var saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)

    var weekStarts = "2018-10-14"
    var weekEnds = "2018-10-20"
    var lunchBreak = ""



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
            empID =  extras.get("userID").toString()
            userName= extras.get("fullname").toString()
        }

        sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
        saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)

        weekStarts=sunday.toString()
        weekEnds=saturday.toString()

        Log.d("sunday",sunday.toString())
        Log.d("saturday",saturday.toString())

        Log.d("wStart",weekStarts)
        Log.d("wEnd",weekEnds)


        getEmployees(urlString)

        GJO(userid, userName,this@Payroll,"https://www.atlanticlawnandgarden.com/cp/app/functions/get/payroll.php",empID,weekStarts,weekEnds)




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


                sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
                saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)

                weekStarts=sunday.toString()
                weekEnds= saturday.toString()

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


                GJO(userid, userName,this@Payroll,"https://www.atlanticlawnandgarden.com/cp/app/functions/get/payroll.php",empID,weekStarts,weekEnds)




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

    fun GJO(ID: String, user: String,context: Context,Url: String, empID: String, weekStarts: String, weekEnds: String){
        val params = HashMap<String, String>()
        params.put("empID", empID)
        params.put("weekStarts", weekStarts)
        params.put("weekEnds", weekEnds)

        Log.d("empID:",empID)
        Log.d("wS:",weekStarts)
        Log.d("  we: ",weekEnds)

        var sumHours =0.0
        var shifts =0

        val stringRequest = object : StringRequest(Request.Method.POST, Url, Response.Listener { s ->
            try {

                lunchBreak = inp_break.text.toString()

                val array = JSONObject(s)
                var shiftsArray = array.getJSONObject("payroll")

                Log.d("Return====",shiftsArray.toString())


                val dateTV = intArrayOf(R.id.tit_date_01, R.id.tit_date_02, R.id.tit_date_03, R.id.tit_date_04, R.id.tit_date_05, R.id.tit_date_06, R.id.tit_date_07)
                val startTV= intArrayOf(R.id.tit_start_01, R.id.tit_start_02, R.id.tit_start_03, R.id.tit_start_04, R.id.tit_start_05, R.id.tit_start_06, R.id.tit_start_07)
                val stopTV = intArrayOf(R.id.tit_stop_01, R.id.tit_stop_02, R.id.tit_stop_03, R.id.tit_stop_04, R.id.tit_stop_05, R.id.tit_stop_06, R.id.tit_stop_07)
                val qtyTV = intArrayOf(R.id.tit_qty_01, R.id.tit_qty_02, R.id.tit_qty_03, R.id.tit_qty_04, R.id.tit_qty_05, R.id.tit_qty_06, R.id.tit_qty_07)

                //sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)

                for(i in 0 until 7){



                    try{
                        val start= shiftsArray.getJSONArray(i.toString())


                        var date=start.getJSONObject(0).getString("startTime")
                        var thisDay=sunday.plusDays(i).toString().substring(5,10)

                        var weekday = sunday.plusDays(i).dayOfWeek().getAsText().substring(0,3)
                        var mweekStarts =thisDay+" ("+weekday+")"

                        var shortDate = mweekStarts


                        var startHour=start.getJSONObject(0).getString("startTime")
                        var shortStartHour = startHour.substring(11,16)

                        var endHour=start.getJSONObject(0).getString("stopTime")
                        var shortEndHour = endHour.substring(11,16)

                        var shiftQty=start.getJSONObject(0).getString("total")
                        var shortShiftQty = shiftQty

                        sumHours += shortShiftQty.toDouble()

                        if (shortShiftQty!="0.00"){

                            val dt= findViewById(dateTV[i]) as TextView
                            dt.setText(shortDate)

                            val strt = findViewById(startTV[i]) as TextView
                            strt.setText(shortStartHour)

                            val stp = findViewById(stopTV[i]) as TextView
                            stp.setText(shortEndHour)

                            val qty = findViewById(qtyTV[i]) as TextView
                            qty.setText(shortShiftQty)
                            shifts++
                        } else {
                            val dt = findViewById(dateTV[i]) as TextView
                            dt.setText(shortDate)

                            val strt = findViewById(startTV[i]) as TextView
                            strt.setText("-------")

                            val stp = findViewById(stopTV[i]) as TextView
                            stp.setText("-------")

                            val qty = findViewById(qtyTV[i]) as TextView
                            qty.setText("-------")

                        }








                    }catch(e: JSONException){
                        var thisDay=sunday.plusDays(i).toString().substring(5,10)
                        var weekday = sunday.plusDays(i).dayOfWeek().getAsText().substring(0,3)
                        var mweekStarts =thisDay+" ("+weekday+")"

                        Log.d("Error~^^^^",e.toString())

                        var shortDate = mweekStarts
                        val dt =  findViewById(dateTV[i]) as TextView
                        dt.setText(shortDate)

                        val strt = findViewById(startTV[i]) as TextView
                        strt.setText("-------")

                        val stp = findViewById(stopTV[i]) as TextView
                        stp.setText("-------")

                        val qty = findViewById(qtyTV[i]) as TextView
                        qty.setText("-------")

                        null
                    }


                }
                val format = DecimalFormat("0.00")
                val formatted = format.format(sumHours)
                totalHoursNum.text = "Totals - Shifts:"+shifts+", Hours:"+formatted


            }catch (e: JSONException){
                e.printStackTrace()
                Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR NEXT WEEK",Toast.LENGTH_LONG).show()
                var spinnerPos = spinnerEmployee.selectedItemPosition
                if(spinnerPos==1){
                    var toast: Toast = Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR LAST WEEK", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    spinnerEmployee.setSelection(0)
                } else {
                    var toast: Toast = Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR THIS WEEK", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    spinnerEmployee.setSelection(1)
                }



                Log.d("NO DATA RETURNED======","V")
            }
        }, Response.ErrorListener {
            error: VolleyError? ->
            try {
                Log.d("ERROR======",error.toString())
            } catch (e: JSONException){
                e.printStackTrace()

            }
        }) {
            override fun getParams(): Map<String, String> = mapOf("empID" to empID,"startDate" to weekStarts,"endDate" to weekEnds)

        }
        var appContext: Context? = null
        val  requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }



}
