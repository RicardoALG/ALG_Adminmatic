package com.atlanticlawnandgarden.alg_adminmatic

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class UsersAdapter(val users: ArrayList<String>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewwType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.dept_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.depth.text = users[position]
    }

    override fun getItemCount() = users.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val depth: TextView = itemView.findViewById(R.id.txt_depth)
    }

}