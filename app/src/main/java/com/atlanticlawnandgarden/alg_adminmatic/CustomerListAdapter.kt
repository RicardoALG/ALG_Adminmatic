package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.android.volley.RequestQueue
import com.squareup.picasso.Picasso

class CustomerListAdapter(private val list: ArrayList<CustomerCard>,
                          private val context: Context): RecyclerView.Adapter<CustomerListAdapter.ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindView(list[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.customer_list_row,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    var volleyRequest: RequestQueue? = null
    var customersList: ArrayList<CustomerCard>? = null
    var customerAdapter: CustomerListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null




    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var customername = itemView.findViewById<TextView>(R.id.customername)
        var address = itemView.findViewById<TextView>(R.id.address)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var linkButton = itemView.findViewById<CardView>(R.id.customerRow)

        fun bindView(customerCard: CustomerCard) {
            customername.text = customerCard.customername
            address.text = customerCard.address


            if (!TextUtils.isEmpty(customerCard.thumbnail)) {
                Picasso.get()
                        .load(customerCard.thumbnail)
                        .placeholder(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .into(thumbnail)
            } else {
                Picasso.get().load(R.drawable.user_placeholder).into(thumbnail)
            }

            linkButton.setOnClickListener {
                var intent = Intent(context, Customer::class.java)
                intent.putExtra("ID",customerCard.link.toString())

                context.startActivity(intent)
            }
        }
    }



}