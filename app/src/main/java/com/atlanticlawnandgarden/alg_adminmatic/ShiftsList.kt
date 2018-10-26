package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_shifts_list.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.joda.time.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat






class ShiftsList : AppCompatActivity() {






    var empID = "test"
    var date = LocalDate()

    var sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
    var saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)

    var weekStarts = "2018-10-14"
    var weekEnds = "2018-10-20"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shifts_list)

        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/shifts.php"
        var extras = intent?.extras

        var fname = "test"

        if( extras !=null){
            empID = extras.get("empID").toString()
            fname = extras.get("fName").toString()
        }

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@ShiftsList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            finish()
        }))

        val shiftsVar = arrayOf("This Week", "Next Week")

        spinnerEmployee.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,shiftsVar)
        spinnerEmployee.setSelection(0,true)




        txt_header.setText(fname+"'s shifts for:")



        weekStarts = sunday.toString()
        weekEnds = saturday.toString()

        getJsonObject(urlString)





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
                    sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).plusDays(6)
                    saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).plusDays(6)

                    weekStarts=sunday.toString()
                    weekEnds=saturday.toString()
                }




                getJsonObject(urlString)


            }

        }

    }



    private fun getJsonObject(Url: String){



        val params = HashMap<String, String>()
        params.put("empID", empID)
        params.put("weekStarts", weekStarts)
        params.put("weekEnds", weekEnds)

        var sumHours =0.0
        var shifts =0

        val stringRequest = object : StringRequest(Request.Method.POST, Url, Response.Listener { s ->
            try {

                val array = JSONObject(s)
                var shiftsArray = array.getJSONObject("shifts")


                val dateTV = intArrayOf(R.id.tit_date_01, R.id.tit_date_02, R.id.tit_date_03, R.id.tit_date_04, R.id.tit_date_05, R.id.tit_date_06, R.id.tit_date_07)
                val startTV= intArrayOf(R.id.tit_start_01, R.id.tit_start_02, R.id.tit_start_03, R.id.tit_start_04, R.id.tit_start_05, R.id.tit_start_06, R.id.tit_start_07)
                val stopTV = intArrayOf(R.id.tit_stop_01, R.id.tit_stop_02, R.id.tit_stop_03, R.id.tit_stop_04, R.id.tit_stop_05, R.id.tit_stop_06, R.id.tit_stop_07)
                val qtyTV = intArrayOf(R.id.tit_qty_01, R.id.tit_qty_02, R.id.tit_qty_03, R.id.tit_qty_04, R.id.tit_qty_05, R.id.tit_qty_06, R.id.tit_qty_07)

                //sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)

                for(i in 0 until 7){



                    try{
                        val start= shiftsArray.getJSONObject(i.toString())

                        var date=start.getString("startTime")
                        var thisDay=sunday.plusDays(i).toString().substring(5,10)

                        var weekday = sunday.plusDays(i).dayOfWeek().getAsText().substring(0,3)
                        weekStarts =thisDay+" ("+weekday+")"

                        var shortDate = weekStarts


                        var startHour=start.getString("startTime")
                        var shortStartHour = startHour.substring(11,16)

                        var endHour=start.getString("endTime")
                        var shortEndHour = endHour.substring(11,16)

                        var shiftQty=start.getString("shiftQty")
                        var shortShiftQty = shiftQty

                        sumHours += shortShiftQty.toDouble()

                        if (shortShiftQty!="0.00"){

                            val dt = findViewById(dateTV[i]) as TextView
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








                    }catch(e:JSONException){
                        var thisDay=sunday.plusDays(i).toString().substring(5,10)
                        var weekday = sunday.plusDays(i).dayOfWeek().getAsText().substring(0,3)
                        weekStarts =thisDay+" ("+weekday+")"

                        var shortDate = weekStarts
                        val dt = findViewById(dateTV[i]) as TextView
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
                shiftsNum.text = "Totals - Shifts:"+shifts+", Hours:"+formatted


            }catch (e: JSONException){
                e.printStackTrace()
                //Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR NEXT WEEK",Toast.LENGTH_LONG).show()
                var spinnerPos = spinnerEmployee.selectedItemPosition
                if(spinnerPos==1){
                    var toast: Toast = Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR NEXT WEEK",Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    spinnerEmployee.setSelection(0)
                } else {
                    var toast: Toast = Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR THIS WEEK",Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.show()
                    spinnerEmployee.setSelection(1)
                }



                Log.d("NO DATA RETURNED======","V")
            }
        }, Response.ErrorListener {
            error: VolleyError? ->
            try {
                Log.d("ERROR======","V")
            } catch (e: JSONException){
                e.printStackTrace()

            }
        }) {
            override fun getParams(): Map<String, String> = mapOf("empID" to empID,"startDate" to weekStarts,"endDate" to weekEnds)

        }

        val  requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)
    }
}
