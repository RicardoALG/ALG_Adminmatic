package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_images_list.*

class ImagesList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@ImagesList, MainMenu::class.java)
            startActivity(clickintent)
        }))
    }
}
