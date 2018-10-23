package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso

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

        Picasso.get()
                .load(model.mPath)
                .placeholder(R.drawable.ic_images)
                .error(R.drawable.ic_images)
                .into(holder.imageView)
        holder.textView.setText(model.customerName)
        holder.imageView.setOnClickListener(({
            Log.d("mama",model.customerName)
      //      holder.imageView.context.startActivity<Gallery_Img>("picPath" to model.mPath, "name" to items.name)

            var intent = Intent(holder.imageView.context, Gallery_Img::class.java)
            intent.putExtra("picPath",model.mPath)


        }))

        holder.imageView.setOnClickListener {
            var intent = Intent(holder.imageView.context, Gallery_Img::class.java)
            intent.putExtra("picPath",model.mPath)
            intent.putExtra("fName","test2")
            holder.imageView.context.startActivity(intent)
        }
    }

    class CustomViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.img_view)
        var textView = itemView.findViewById<TextView>(R.id.txt_customer)
    }
}