package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_schedule_list.*

class ScheduleList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_list)

        btn_edit.setOnClickListener(({
            var clickintent = Intent(this@ScheduleList, MainMenu::class.java)
            startActivity(clickintent)
        }))


        btn_schedule_settings.setOnClickListener(({
            var clickintent = Intent(this@ScheduleList, ScheduleSettings::class.java)
            startActivity(clickintent)
        }))
    }
}
