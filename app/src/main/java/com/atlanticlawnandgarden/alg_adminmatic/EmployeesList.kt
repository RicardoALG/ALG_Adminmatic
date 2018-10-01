package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_employees_list.*

class EmployeesList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employees_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@EmployeesList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@EmployeesList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        CustomerBtn.setOnClickListener(({
            var clickintent = Intent(this@EmployeesList, Employee::class.java)
            startActivity(clickintent)
        }))
    }
}
