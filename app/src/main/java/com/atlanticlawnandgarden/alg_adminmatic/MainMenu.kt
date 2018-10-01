package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        btn_customers.setOnClickListener(({
            var clickintent = Intent(this@MainMenu, CustomersList::class.java)
            startActivity(clickintent)
        }))

        btn_employees.setOnClickListener(({
            var clickintent = Intent(this@MainMenu, EmployeesList::class.java)
            startActivity(clickintent)
        }))

        btn_vendors.setOnClickListener(({
            var clickintent = Intent(this@MainMenu, VendorList::class.java)
            startActivity(clickintent)
        }))

        btn_items.setOnClickListener(({
            var clickintent = Intent(this@MainMenu, ItemsList::class.java)
            startActivity(clickintent)
        }))

    }
}
