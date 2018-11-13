package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Context
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.atlanticlawnandgarden.alg_adminmatic.R.id.shiftsNum
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONArray
import java.text.DecimalFormat
import java.util.HashMap

class WSStart  (empID: String, startTime: String, startDate: String, cont: Context){
    var SID = ""

   init {
       var empID = empID
       var startTime = startTime
       var startDate = startDate
       var Url = "https://www.atlanticlawnandgarden.com/cp/app/functions/update/workShiftStart.php?empID=" + empID + "&startTime=" + startTime + "&startDate=" + startDate



//        val params = HashMap<String, String>()
//        params.put("empID", empID)
//        params.put("startTime", startTime)
//        params.put("startDate", startDate)

       var sumHours = 0.0
       var shifts = 0

       val stringRequest = object : StringRequest(Request.Method.GET, Url, Listener { s ->
           try {

               val array = JSONArray(s)
               var shiftsArray = array.getJSONObject(0)

               //sunday = date.withDayOfWeek(DateTimeConstants.MONDAY).minusDays(1)


               try {
                   //val start= shiftsArray.getJSONArray(0.toString())

                   var shiftID = shiftsArray.getString("ID")

                   Log.d("SHIFT ID:===", shiftID)
                   SID = shiftID


                   //Toast.makeText(this,"shift ID:",Toast.LENGTH_LONG).show()

                   //sumHours += shortShiftQty.toDouble()

               } catch (e: JSONException) {
                   Log.d("JSON EXCEPTION", e.toString())

                   null
               }
               val format = DecimalFormat("0.00")
               val formatted = format.format(sumHours)
               //shiftsNum.text = "Totals - Shifts:"+shifts+", Hours:"+formatted


           } catch (e: JSONException) {
               e.printStackTrace()
               //Toast.makeText(this,"THERE ARE NO SHIFTS PROGRAMMED FOR NEXT WEEK",Toast.LENGTH_LONG).show()
               Log.d("NO DATA RETURNED======", "V")
           }
       }, Response.ErrorListener { error: VolleyError? ->
           try {
               Log.d("ERROR======", "V")
           } catch (e: JSONException) {
               e.printStackTrace()

           }
       }) {
           //override fun getParams(): Map<String, String> = mapOf("empID" to empID,"startDate" to startDate,"startTime" to startTime)

       }

       val requestQueue = Volley.newRequestQueue(cont)
       requestQueue.add<String>(stringRequest)


   }


}