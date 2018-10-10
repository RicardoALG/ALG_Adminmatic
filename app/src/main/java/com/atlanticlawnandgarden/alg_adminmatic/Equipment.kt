package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_equipment.*

class Equipment : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_equipment)

        btn_back.setOnClickListener(({
            var clickintent = Intent(this@Equipment, EquipmentList::class.java)
            startActivity(clickintent)
        }))

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@Equipment, MainMenu::class.java)
            startActivity(clickintent)
        }))
    }
}
