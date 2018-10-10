package com.atlanticlawnandgarden.alg_adminmatic

import android.app.DatePickerDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_performance_list.*
import java.util.*

class PerformanceList : AppCompatActivity() {

    lateinit var option : Spinner
    lateinit var result : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@PerformanceList, MainMenu::class.java)
            startActivity(clickintent)
        }))


        btn_back.setOnClickListener(({
            var clickintent = Intent(this@PerformanceList, MainMenu::class.java)
            startActivity(clickintent)
        }))


        //Calendar

        val c= Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        date_from.onFocusChangeListener

        date_from.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { _: DatePicker, mYear, mMonth, mDay ->

                date_from.setText(""+(mMonth +1) +"/"+ mDay + "/" + mYear )},year,month,day)

            dpd.show()
        }

        date_to.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { _: DatePicker, mYear, mMonth, mDay ->

                date_to.setText(""+ (mMonth +1) +"/"+ mDay + "/" + mYear )},year,month,day)

            dpd.show()
        }
    }
}
