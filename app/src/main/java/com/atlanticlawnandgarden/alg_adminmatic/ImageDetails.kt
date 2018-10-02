package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_image_details.*

class ImageDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@ImageDetails, ImagesList::class.java)
            startActivity(clickintent)
        }))
    }
}
