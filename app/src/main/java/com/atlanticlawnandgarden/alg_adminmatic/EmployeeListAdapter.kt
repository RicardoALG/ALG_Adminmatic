package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class EmployeeListAdapter(private val list: ArrayList<EmployeeCard>,
                          private val context: Context): RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bindView(list[p1])
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.list_row,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }




    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var title = itemView.findViewById<TextView>(R.id.employee)
        var thumbnail = itemView.findViewById<ImageView>(R.id.thumbnail)
        var linkButton = itemView.findViewById<CardView>(R.id.employeeRow)

        fun bindView(employeeCard: EmployeeCard) {
            title.text = employeeCard.username
            linkButton.setOnClickListener {
                var intent = Intent(this,EmployeesList::class.java)
                startActivity(intent)
            }

            if (!TextUtils.isEmpty(employeeCard.thumbnail)) {
                Picasso.get()
                        .load(employeeCard.thumbnail)
                        .placeholder(R.drawable.user_placeholder)
                        .error(R.drawable.user_placeholder)
                        .into(thumbnail)
            } else {
                Picasso.get().load(R.drawable.user_placeholder).into(thumbnail)
            }
        }
    }

}