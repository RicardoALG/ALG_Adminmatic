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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_department_crew_list.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject







class DepartmentCrewList : AppCompatActivity()  {

    var volleyRequest: RequestQueue? = null
    var departmentslist: ArrayList<EmployeeCard>? = null
    var departmentsAdapter: DepCrewAdapter? = null
    var employeesList: ArrayList<EmployeeCard>? = null
    var employeeAdapter: DepCrewAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_crew_list)





        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/departments.php"

        departmentslist = ArrayList<EmployeeCard>()
        employeesList = ArrayList<EmployeeCard>()
        volleyRequest = Volley.newRequestQueue(this)


        getJsonObject(urlString)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@DepartmentCrewList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            finish()
        }))

    }



    private fun getJsonObject(Url: String){

        var extras = intent.extras
        var empID = extras.get("empID").toString()
        var crewView = extras.get("crewView").toString()

        val params = HashMap<String, String>()
        params.put("empID", empID)
        params.put("crewView", crewView)



        val stringRequest = object : StringRequest(Request.Method.POST, Url, Response.Listener { s ->
            try {

                val array = JSONObject(s)

                var departmentsArray: JSONArray
                var empNum: Number =0
                var iddep: String

                if(crewView=="0"){
                    departmentsArray = array.getJSONArray("departments")
                    //shiftsNum.text = empNum.toString()+" Department(s)"
                    iddep="id"
                    tit_depcrew.setText(R.string.departments)
                    empNum = departmentsArray.length()
                    shiftsNum.text = empNum.toString()+" Department(s)"

                } else{
                    departmentsArray = array.getJSONArray("crews")

                    //shiftsNum.text = empNum.toString()+" Crew(s)"
                    iddep="dep"
                    tit_depcrew.setText(R.string.crews)
                    empNum = departmentsArray.length()
                    shiftsNum.text = empNum.toString()+" Crew(s)"
                }


                Log.d("ReturnJSON======",departmentsArray.toString())
                for(i in 0..departmentsArray.length() -1 ){

                    val departmentName = departmentsArray.getJSONObject(i).getString("name")
                    var departmentsCount = departmentsArray.getJSONObject(i).getJSONArray("employees")
                    var deptColor = departmentsArray.getJSONObject(i).getString(iddep)

                    //////////////////GENERATE DEPARTMENT ROW
                    var deptBackgroundColorString = "https://www.atlanticlawnandgarden.com/uploads/dept_colors/"+deptColor+".gif"

                    var departmentCard = EmployeeCard()
                    departmentCard.username = departmentName

                    departmentCard.thumbnail=deptBackgroundColorString



                    departmentslist!!.add(departmentCard)

                    departmentsAdapter = DepCrewAdapter(departmentslist!!,this)
                    layoutManager = LinearLayoutManager(this)

                    //setup list

                    depcrew_list.layoutManager = layoutManager
                    depcrew_list.adapter = departmentsAdapter


                    for(j in 0..departmentsCount.length() -1){
                        val employeeName = departmentsCount.getJSONObject(j).getString("name")
                        val employeePic = departmentsCount.getJSONObject(j).getString("pic")



                        var employeePicString = "https://www.atlanticlawnandgarden.com/uploads/general/"+employeePic
                        //var employeeID = employeeName.getString("sort")

                        /////////////////////GENERATE EMPLOYEE ROW


                        var employeeCard = EmployeeCard()
                        employeeCard.username = employeeName
                        employeeCard.thumbnail = employeePicString

                        departmentslist!!.add(employeeCard)


                        employeeAdapter = DepCrewAdapter(departmentslist!!,this)
                        layoutManager = LinearLayoutManager(this)

                        //setup list

                        depcrew_list.layoutManager = layoutManager
                        depcrew_list.adapter = employeeAdapter

                    }

                }

                departmentsAdapter!!.notifyDataSetChanged()
                //employeeAdapter!!.notifyDataSetChanged()


            }catch (e: JSONException){e.printStackTrace()}
        }, Response.ErrorListener {
            error: VolleyError? ->
            try {

            } catch (e: JSONException){
                e.printStackTrace()
            }
        }) {
            override fun getParams(): Map<String, String> = mapOf("empID" to empID,"crewView" to crewView)

        }

        val  requesQueue = Volley.newRequestQueue(this)
        requesQueue.add<String>(stringRequest)
    }



}
