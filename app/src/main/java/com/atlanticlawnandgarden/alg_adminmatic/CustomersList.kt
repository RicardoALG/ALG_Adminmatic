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
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_customers_list.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class CustomersList : AppCompatActivity() {

    var volleyRequest: RequestQueue? = null
    var customersList: ArrayList<CustomerCard>? = null
    var customerAdapter: CustomerListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@CustomersList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/customers.php"

        customersList = ArrayList<CustomerCard>()
        volleyRequest = Volley.newRequestQueue(this)

        getCustomer(urlString)

    }

    fun getCustomer(url: String){
        val customerRequest = JsonObjectRequest(Request.Method.GET,
                url, Response.Listener {
            response: JSONObject ->
            try{
                val resultArray = response.getJSONArray("customers")
                var custNum = resultArray.length()

                customersNum.text = custNum.toString()+" Active Customers"

                for(i in 0..resultArray.length() -1){
                    var customerObj = resultArray.getJSONObject(i)


                    var customername = customerObj.getString("name")
                    var address = customerObj.getString("mainAddr")
                    //var customerPic = "https://www.atlanticlawnandgarden.com/uploads/general/"+customerObj.getString("pic")
                    var customerID = customerObj.getString("ID")

                    //Check if Info is being pulled from server
                    //Log.d("Result ===>", employeeName)


                    var customerCard = CustomerCard()
                    customerCard.customername = customername
                    customerCard.address=address
                    //customerCard.thumbnail = customerPic
                    customerCard.link = customerID

                    customersList!!.add(customerCard)

                    customerAdapter = CustomerListAdapter(customersList!!,this)
                    layoutManager = LinearLayoutManager(this)

                    //setup list

                    recyclerViewCustomers.layoutManager = layoutManager
                    recyclerViewCustomers.adapter = customerAdapter
                }

                customerAdapter!!.notifyDataSetChanged()

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


        volleyRequest!!.add(customerRequest)
    }
}
