//package com.atlanticlawnandgarden.alg_adminmatic
//
//import android.content.Context
//import android.util.Log
//import android.view.Gravity
//import android.widget.TextView
//import android.widget.Toast
//import com.android.volley.Request
//import com.android.volley.Response
//import com.android.volley.VolleyError
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import com.google.android.gms.common.internal.ServiceSpecificExtraArgs
//import kotlinx.android.synthetic.main.activity_payroll.*
//import org.joda.time.DateTimeConstants
//import org.joda.time.LocalDate
//import org.json.JSONException
//import org.json.JSONObject
//import java.security.Permission
//import java.text.DecimalFormat
//import java.util.HashMap
//
//class GetJsonObj(ID: String, user: String,context: Context,Url: String, empID: String, weekStarts: String, weekEnds: String){
//    var empID = ID
//    var date = LocalDate()
//
//    var sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
//    var saturday = date.withDayOfWeek(DateTimeConstants.SUNDAY).minusDays(1)
//
//    var weekStarts = "2018-10-14"
//    var weekEnds = "2018-10-20"
//
//
//
//
//    init{
//        val params = HashMap<String, String>()
//        params.put("empID", empID)
//        params.put("weekStarts", weekStarts)
//        params.put("weekEnds", weekEnds)
//
//        Log.d("empID:",empID)
//        Log.d("wS:",weekStarts)
//        Log.d("  we: ",weekEnds)
//
//        var sumHours =0.0
//        var shifts =0
//
//        val stringRequest = object : StringRequest(Request.Method.POST, Url, Response.Listener { s ->
//            try {
//
//                val array = JSONObject(s)
//                var shiftsArray = array.getJSONObject("shifts")
//
//                Log.d("Return====",shiftsArray.toString())
//
//
//                val dateTV = intArrayOf(R.id.tit_date_01, R.id.tit_date_02, R.id.tit_date_03, R.id.tit_date_04, R.id.tit_date_05, R.id.tit_date_06, R.id.tit_date_07)
//                val startTV= intArrayOf(R.id.tit_start_01, R.id.tit_start_02, R.id.tit_start_03, R.id.tit_start_04, R.id.tit_start_05, R.id.tit_start_06, R.id.tit_start_07)
//                val stopTV = intArrayOf(R.id.tit_stop_01, R.id.tit_stop_02, R.id.tit_stop_03, R.id.tit_stop_04, R.id.tit_stop_05, R.id.tit_stop_06, R.id.tit_stop_07)
//                val qtyTV = intArrayOf(R.id.tit_qty_01, R.id.tit_qty_02, R.id.tit_qty_03, R.id.tit_qty_04, R.id.tit_qty_05, R.id.tit_qty_06, R.id.tit_qty_07)
//
//                //sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)
//
//                for(i in 0 until 7){
//
//
//
//                    try{
//                        val start= shiftsArray.getJSONObject(i.toString())
//
//                        var date=start.getString("startTime")
//                        var thisDay=sunday.plusDays(i).toString().substring(5,10)
//
//                        var weekday = sunday.plusDays(i).dayOfWeek().getAsText().substring(0,3)
//                        //weekStarts =thisDay+" ("+weekday+")"
//
//                        var shortDate = weekStarts
//
//
//                        var startHour=start.getString("startTime")
//                        var shortStartHour = startHour.substring(11,16)
//
//                        var endHour=start.getString("endTime")
//                        var shortEndHour = endHour.substring(11,16)
//
//                        var shiftQty=start.getString("shiftQty")
//                        var shortShiftQty = shiftQty
//
//                        sumHours += shortShiftQty.toDouble()
//
//                        if (shortShiftQty!="0.00"){
//
//                            val dt= Payroll().findViewById(dateTV[i]) as TextView
//                            dt.setText(shortDate)
//
//                            val strt = Payroll().findViewById(startTV[i]) as TextView
//                            strt.setText(shortStartHour)
//
//                            val stp = Payroll().findViewById(stopTV[i]) as TextView
//                            stp.setText(shortEndHour)
//
//                            val qty = Payroll().findViewById(qtyTV[i]) as TextView
//                            qty.setText(shortShiftQty)
//                            shifts++
//                        } else {
//                            val dt = Payroll().findViewById(dateTV[i]) as TextView
//                            dt.setText(shortDate)
//
//                            val strt = Payroll().findViewById(startTV[i]) as TextView
//                            strt.setText("-------")
//
//                            val stp = Payroll().findViewById(stopTV[i]) as TextView
//                            stp.setText("-------")
//
//                            val qty = Payroll().findViewById(qtyTV[i]) as TextView
//                            qty.setText("-------")
//
//                        }
//
//
//
//
//
//
//
//
//                    }catch(e: JSONException){
//                        var thisDay=sunday.plusDays(i).toString().substring(5,10)
//                        var weekday = sunday.plusDays(i).dayOfWeek().getAsText().substring(0,3)
//                        //weekStarts =thisDay+" ("+weekday+")"
//
//                        Log.d("Error~^^^^",e.toString())
//
//                        var shortDate = weekStarts
//                        val dt =  Payroll().findViewById(dateTV[i]) as TextView
//                        dt.setText(shortDate)
//
//                        val strt = Payroll().findViewById(startTV[i]) as TextView
//                        strt.setText("-------")
//
//                        val stp = Payroll().findViewById(stopTV[i]) as TextView
//                        stp.setText("-------")
//
//                        val qty = Payroll().findViewById(qtyTV[i]) as TextView
//                        qty.setText("-------")
//
//                        null
//                    }
//
//
//                }
//                val format = DecimalFormat("0.00")
//                val formatted = format.format(sumHours)
//                //com.atlanticlawnandgarden.alg_adminmatic.Payroll().totalHoursNum.text = "Totals - Shifts:"+shifts+", Hours:"+formatted
//
//
//            }catch (e: JSONException){
//                e.printStackTrace()
//                //Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR NEXT WEEK",Toast.LENGTH_LONG).show()
//                var spinnerPos = Payroll().spinnerEmployee.selectedItemPosition
//                if(spinnerPos==1){
////                    var toast: Toast = Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR NEXT WEEK", Toast.LENGTH_LONG)
////                    toast.setGravity(Gravity.CENTER, 0, 0)
////                    toast.show()
//                    Payroll().spinnerEmployee.setSelection(0)
//                } else {
////                    var toast: Toast = Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR THIS WEEK", Toast.LENGTH_LONG)
////                    toast.setGravity(Gravity.CENTER, 0, 0)
////                    toast.show()
//                    Payroll().spinnerEmployee.setSelection(1)
//                }
//
//
//
//                Log.d("NO DATA RETURNED======","V")
//            }
//        }, Response.ErrorListener {
//            error: VolleyError? ->
//            try {
//                Log.d("ERROR======",error.toString())
//            } catch (e: JSONException){
//                e.printStackTrace()
//
//            }
//        }) {
//            override fun getParams(): Map<String, String> = mapOf("empID" to empID,"startDate" to weekStarts,"endDate" to weekEnds)
//
//        }
//        var appContext: Context? = null
//        val  requestQueue = Volley.newRequestQueue(context)
//        requestQueue.add<String>(stringRequest)
//    }
//
//}