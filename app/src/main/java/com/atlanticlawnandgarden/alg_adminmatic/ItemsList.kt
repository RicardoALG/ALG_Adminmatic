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
import kotlinx.android.synthetic.main.activity_items_list.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception

class ItemsList : AppCompatActivity() {


    var volleyRequest: RequestQueue? = null
    var itemsList: ArrayList<ItemCard>? = null
    var itemAdapter: ItemListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_items_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@ItemsList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        var urlString = "https://www.atlanticlawnandgarden.com/cp/app/functions/get/items.php"

        itemsList = ArrayList<ItemCard>()
        volleyRequest = Volley.newRequestQueue(this)

        getItem(urlString)

    }

    fun getItem(url: String){
        val itemRequest = JsonObjectRequest(Request.Method.GET,
                url, Response.Listener {
            response: JSONObject ->
            try{
                val resultArray = response.getJSONArray("items")
                var itemNum = resultArray.length()

                itemsNum.text = itemNum.toString()+" Active Items"

                for(i in 0..resultArray.length() -1){
                    var itemObj = resultArray.getJSONObject(i)


                    var itemname = itemObj.getString("name")
                    var type = itemObj.getString("type")
                    var price = itemObj.getString("price")
                    var unit = itemObj.getString("unit")
                    var itemID = itemObj.getString("ID")

                    //Check if Info is being pulled from server
                    //Log.d("Result ===>", employeeName)


                    var itemCard = ItemCard()
                    itemCard.itemname = itemname
                    itemCard.type=type
                    itemCard.price = price
                    itemCard.unit = unit
                    itemCard.itemID = itemID

                    itemsList!!.add(itemCard)

                    itemAdapter = ItemListAdapter(itemsList!!,this)
                    layoutManager = LinearLayoutManager(this)

                    //setup list

                    recyclerViewItems.layoutManager = layoutManager
                    recyclerViewItems.adapter = itemAdapter
                }

                itemAdapter!!.notifyDataSetChanged()

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


        volleyRequest!!.add(itemRequest)
    }
}
