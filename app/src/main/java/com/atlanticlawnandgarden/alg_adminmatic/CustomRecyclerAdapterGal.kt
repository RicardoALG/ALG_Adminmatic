package com.atlanticlawnandgarden.alg_adminmatic

import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.File

class CustomRecyclerAdapterGal(imageModel:ArrayList<ImageModel>):RecyclerView.Adapter<CustomRecyclerAdapterGal.CustomViewHolder>() {
    var imgModel = imageModel



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.emp_img,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("IMAGE:::",imgModel.size.toString())
        return imgModel.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val model = imgModel[position]
        //Picasso.get().load(File(model.mPath)).into(holder.imageView)
        Picasso.get()
                .load(model.mPath)
                .placeholder(R.drawable.ic_images)
                .error(R.drawable.ic_images)
                .into(holder.imageView)
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.img_view)
    }
}