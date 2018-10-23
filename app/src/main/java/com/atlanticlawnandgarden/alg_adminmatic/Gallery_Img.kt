package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_gallery__img.*

class Gallery_Img : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery__img)

        var extras = intent.extras
        var picPath = extras.get("picPath").toString()
//        var customer = extras.get("customer").toString()
//        var likes = extras.get("likes").toString()
//        var  description = extras.get("description").toString()

        if( extras != null){
            Picasso.get()
                    .load(picPath)
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder)
                    .into(img_gal)

        }
        Log.d("url",picPath)


    }
}
