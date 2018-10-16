package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import kotlinx.android.synthetic.main.activity_shifts_list.*
import android.widget.TextView



class ShiftsList : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shifts_list)

        btn_home.setOnClickListener(({
            var clickintent = Intent(this@ShiftsList, MainMenu::class.java)
            startActivity(clickintent)
        }))

        btn_back.setOnClickListener(({
            finish()
        }))

        val shiftsVar = arrayOf("This Week", "Next Week")

        spinnerWeeks.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,shiftsVar)
        spinnerWeeks.setSelection(0,true)

        var extras = intent.extras
        var empID = extras.get("empID").toString()
        var fname = extras.get("fName").toString()

        txt_header.setText(fname+"'s shifts for:")



        spinnerWeeks.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(this@ShiftsList, shiftsVar[position], LENGTH_LONG).show()
            }

        }

    }
}
