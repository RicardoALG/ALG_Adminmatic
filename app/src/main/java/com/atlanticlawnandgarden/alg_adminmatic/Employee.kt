package com.atlanticlawnandgarden.alg_adminmatic

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_employee.*
import kotlinx.android.synthetic.main.fragment_frag_employee.*
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class Employee : AppCompatActivity() {

    var paths: ArrayList<String> =ArrayList()
    var customers: ArrayList<String> =ArrayList()
    var likes: ArrayList<String> =ArrayList()
    var descriptions: ArrayList<String> =ArrayList()

    var volleyRequest: RequestQueue? = null
    val employeeURL = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/employeeInfo.php?empID="
    var id = "229"
    var picURLprefix = "https://www.atlanticlawnandgarden.com/uploads/general/"

    ////////////GALLERY STUFF

    val REQUEST_CODE = 1

    var customRecyclerAdapter:CustomRecyclerAdapterGal?=null
    var imageModel:ArrayList<ImageModel> = ArrayList()
    var picName = ""
    var fullname =""
    var empName = ""
    var user = ""






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee)
        volleyRequest = Volley.newRequestQueue(this)

        val imgPhpURL = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/images.php"

        getJsonObjectImg(imgPhpURL)


        ////////////////CHECK GALLERY PERMISSIONS

        //checking permission
        val hasPermission = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(hasPermission!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this@Employee, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
        }else{

            RetrievingImages()
            settingUpAdapter()
        }

        var extras = intent.extras

        if( extras != null){
            id = extras.get("ID").toString()
        }

        getJsonObject(employeeURL+id)

        employeePic.setOnClickListener (({
            var clickintent = Intent(this@Employee, EmployeePortrait::class.java)
            clickintent.putExtra("picPath",picURLprefix+picName)
            clickintent.putExtra("empName",empName)
            startActivity(clickintent)

        }))

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@Employee, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@Employee, EmployeesList::class.java)
            startActivity(clickintent)
        }))

        btn_login.setOnClickListener(({
            var clickintent = Intent(this@Employee, EmployeeLogin::class.java)
            clickintent.putExtra("userName",user)
            startActivity(clickintent)
        }))

        btnPayroll.setOnClickListener(({
            var clickintent = Intent(this@Employee, Payroll::class.java)
            clickintent.putExtra("userID",id)
            clickintent.putExtra("fullname",fullname)
            startActivity(clickintent)
        }))


    }

    fun getJsonObject(Url:String){
        val jsonObjectReq = JsonObjectRequest(Request.Method.GET,Url,
                Response.Listener {
                    response: JSONObject ->
                    try{
                        val employeesArray = response.getJSONArray("employees")



                            var propertyObj = employeesArray.getJSONObject(0)
                            var id = propertyObj.getString("ID")
                            var name = propertyObj.getString("name")
                            fullname = name
                            var lname = propertyObj.getString("lname")
                            var fname = propertyObj.getString("fname")
                            empName = fname
                            var userName = propertyObj.getString("username")
                            user = userName
                            var pic = propertyObj.getString("pic")
                            picName = pic
                            var phone = propertyObj.getString("phone")
                            var email = propertyObj.getString("email")

                            var cPhone = phone

                            var cleanPhone = Regex("[^A-Za-z0-9 ]")
                            cPhone = cleanPhone.replace(cPhone,"")

                        ///////POPULATE ACTIVITY

                        txt_employee_name.text = name
                        txt_phone.text = phone
                        txt_email.text = email


                            Picasso.get()
                                    .load(picURLprefix+pic)
                                    .placeholder(R.drawable.user_placeholder)
                                    .error(R.drawable.user_placeholder)
                                    .into(employeePic)

                        layout_phone_btn.setOnClickListener {

                            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cPhone))
                            startActivity(intent)
                        }



                        layout_email_btn.setOnClickListener {

                            val intent = Intent(Intent.ACTION_SENDTO)
                            intent.type = "message/rfc822"
                            intent.putExtra(Intent.EXTRA_EMAIL, email)
                            intent.data = Uri.parse("mailto:$email")
                            intent.putExtra(Intent.EXTRA_SUBJECT, "From AdminMatic")
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.addFlags(Intent.FLAG_FROM_BACKGROUND)
                            try {

                                startActivity(intent)
                            } catch (e: android.content.ActivityNotFoundException) {
                                e.printStackTrace()
                                Log.d("Email error:", e.toString())
                            }

                        }

                        btnDepartments.setOnClickListener {
                            var intent = Intent(this, DepartmentCrewList::class.java)
                            intent.putExtra("empID",id)
                            intent.putExtra("crewView",0)

                            startActivity(intent)
                        }

                        btnCrews.setOnClickListener {
                            var intent = Intent(this, DepartmentCrewList::class.java)
                            intent.putExtra("empID",id)
                            intent.putExtra("crewView",1)

                            startActivity(intent)
                        }

                        btnShifts.setOnClickListener {
                            var intent = Intent(this, ShiftsList::class.java)
                            intent.putExtra("empID",id)
                            intent.putExtra("fName",fname)

                            startActivity(intent)
                        }


                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener {
                    error: VolleyError? ->
                    try {
                        Log.d("Error",error.toString())
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                })
        volleyRequest!!.add(jsonObjectReq)
    }

    //retrieving images
    fun RetrievingImages(){

        val imgFilePath = "https://www.atlanticlawnandgarden.com/uploads/general/medium/"

        var listOfPaths = paths
        var listOfCustomers = customers
        Log.d("listOfPathsCount ",listOfPaths.count().toString())

        var imagePath =""
        var customer=""
        var img_likes=""
        var img_desc=""

        Log.d("Just beforeloadingURLs",paths.count().toString())
        for(i in 0 until listOfPaths.count() ){

            imagePath = imgFilePath+listOfPaths[i] ///  <----filepath to image
            customer = listOfCustomers[i]
            img_likes = likes[i]
            img_desc = descriptions[i]

            imageModel.add(ImageModel(imagePath,customer,img_likes,img_desc))
            /////////////////////////////////////////////////////////////////




        }
    }

    //setting up adapter and recyclerview
    fun settingUpAdapter(){
        customRecyclerAdapter = CustomRecyclerAdapterGal(imageModel)
        val layoutManager = GridLayoutManager(applicationContext,3)
        employee_gallery.layoutManager = layoutManager
        employee_gallery.adapter = customRecyclerAdapter
    }

    //overriding onRequestPermissionResult
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE) {
            if ((grantResults[0] and grantResults.size) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getJsonObjectImg(Url: String){

        val stringRequest = object : StringRequest(Request.Method.POST, Url, Response.Listener { s ->
            try {

                val array = JSONObject(s)
                var imagesArray = array.getJSONArray("images")

                for(i in 0..imagesArray.length() -1 ){
                    var imgURL = imagesArray.getJSONObject(i).getString("fileName")
                    var customerName = imagesArray.getJSONObject(i).getString("customerName")
                    var imgLikes = imagesArray.getJSONObject(i).getString("likes")
                    var imgDescription = imagesArray.getJSONObject(i).getString("description")
                    paths.add(imgURL)
                    customers.add(customerName)
                    likes.add(imgLikes)
                    descriptions.add(imgDescription)


                }

                RetrievingImages()
                settingUpAdapter()
            } catch(e:JSONException){
                Log.d("ERROR------", e.toString())

                null
            }

            }, Response.ErrorListener {
            error: VolleyError? ->
            try {
                Log.d("ERROR======","V")
            } catch (e: JSONException){
                e.printStackTrace()
            }
        }) {
            override fun getParams(): Map<String, String> = mapOf("uploadedBy" to id)

        }
        val  requestQueue = Volley.newRequestQueue(this)
        requestQueue.add<String>(stringRequest)

    }


}
