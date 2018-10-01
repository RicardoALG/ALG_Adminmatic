package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_customers_list.*

class CustomersList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customers_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@CustomersList, MainMenu::class.java)
            startActivity(clickintent)
        }))


        CustomerBtn.setOnClickListener(({
            var clickintent = Intent(this@CustomersList, Customer::class.java)
            startActivity(clickintent)
        }))


    }
}
