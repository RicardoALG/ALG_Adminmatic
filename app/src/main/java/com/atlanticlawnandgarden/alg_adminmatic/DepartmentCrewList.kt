package com.atlanticlawnandgarden.alg_adminmatic

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
import kotlinx.android.synthetic.main.activity_department_crew_list.*
import org.json.JSONException
import org.json.JSONObject







class DepartmentCrewList : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var departmentslist: ArrayList<EmployeeCard>? = null
    var departmentsAdapter: DepCrewAdapter? = null
    var employeesList: ArrayList<EmployeeCard>? = null
    var employeeAdapter: DepCrewAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_crew_list)





        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/ZZZdepartments.php"

        departmentslist = ArrayList<EmployeeCard>()
        employeesList = ArrayList<EmployeeCard>()
        volleyRequest = Volley.newRequestQueue(this)

        //getEmployee(urlString)
        getJsonObject(urlString)
    }

    private fun getJsonObject(Url: String){

        var extras = intent.extras
        var empID = extras.get("empID").toString()
        var crewView = extras.get("crewView").toString()
//        var params = JSONObject()
//        params.put("empID", "247")
//        params.put("crewView", "0")

        Log.d("EMPID======>",empID)
        Log.d("CrewVIEW======>",crewView)

        val params = HashMap<String, String>()
        params.put("empID", empID)
        params.put("crewView", crewView)

        val parameters = JSONObject(params)
Log.d("Verdolagon",Url+"?empID="+empID+"&crewView="+crewView)

        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url+"?empID="+empID+"&crewView="+crewView,
                        Response.Listener{
                            response: JSONObject ->
                            try {

                                var departmentsArray = response.getJSONArray("departments")
                                var empNum = departmentsArray.length()

                                departmentsNum.text = empNum.toString()+" Department(s)"

                                Log.d("ReturnJSON======",departmentsArray.toString())
                                for(i in 0..departmentsArray.length() -1 ){
                                    val departmentName = departmentsArray.getJSONObject(i).getString("name")
                                    var departmentsCount = departmentsArray.getJSONObject(i).getJSONArray("employees")
                                    Log.d("Departments:", departmentName)
                                    Log.d("Departments Count:", departmentsArray.length().toString())
                                    Log.d( " Employees Count:", departmentsCount.length().toString())

                                    //////////////////GENERATE DEPARTMENT ROW


                                    //var departmentPic = departmentName.getString("color")
                                    var departmentCard = EmployeeCard()
                                    departmentCard.username = departmentName
                                    //departmentCard.thumbnail = departmentPic

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

                                        employeesList!!.add(employeeCard)


                                        employeeAdapter = DepCrewAdapter(employeesList!!,this)
                                        layoutManager = LinearLayoutManager(this)

                                        //setup list

                                        depcrew_list.layoutManager = layoutManager
                                        depcrew_list.adapter = employeeAdapter

                                    }

                                }

                                departmentsAdapter!!.notifyDataSetChanged()
                                //employeeAdapter!!.notifyDataSetChanged()

                            }catch (e: JSONException){e.printStackTrace()}

                        },
                        Response.ErrorListener {
                            error: VolleyError? ->
                            try {

                            } catch (e: JSONException){
                                e.printStackTrace()
                            }
                        })


        volleyRequest!!.add(jsonObjectReq)
    }




}