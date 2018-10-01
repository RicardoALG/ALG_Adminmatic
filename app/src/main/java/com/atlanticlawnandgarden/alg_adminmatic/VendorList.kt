package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_vendor_list.*

class VendorList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_list)

        ItemBtn.setOnClickListener(({
            var clickintent = Intent(this@VendorList, Vendor::class.java)
            startActivity(clickintent)
        }))

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@VendorList, MainMenu::class.java)
            startActivity(clickintent)
        }))

    }
}
