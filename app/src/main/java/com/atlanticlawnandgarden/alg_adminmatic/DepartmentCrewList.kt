package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_department_crew_list.*
import org.json.JSONArray
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

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@DepartmentCrewList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@DepartmentCrewList, EmployeesList::class.java)
            startActivity(clickintent)
        }))
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
Log.d("URL",Url+"?empID="+empID+"&crewView="+crewView)

        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url+"?empID="+empID+"&crewView="+crewView,
                        Response.Listener{
                            response: JSONObject ->
                            try {
                                var departmentsArray: JSONArray
                                var empNum: Number

                                if(crewView=="0"){
                                    departmentsArray = response.getJSONArray("departments")
                                    empNum = departmentsArray.length()

                                    departmentsNum.text = empNum.toString()+" Department(s)"
                                } else {
                                    departmentsArray = response.getJSONArray("crews")
                                    empNum = departmentsArray.length()

                                    departmentsNum.text = empNum.toString()+" Crew(s)"

                                }

                                Log.d("ReturnJSON======",departmentsArray.toString())
                                for(i in 0..departmentsArray.length() -1 ){
                                    val departmentName = departmentsArray.getJSONObject(i).getString("name")
                                    var departmentsCount = departmentsArray.getJSONObject(i).getJSONArray("employees")
                                    var deptColor = departmentsArray.getJSONObject(i).getJSONArray("id")

                                    //////////////////GENERATE DEPARTMENT ROW
                                    var deptBackgroundColorString = "https://www.atlanticlawnandgarden.com/uploads/dept_colors/"+deptColor+".gif"
                                    //var thumbnail2 = this.findViewById<ImageView>(R.id.thumbnail)
                                    //var departmentPic = departmentName.getString("color")
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
