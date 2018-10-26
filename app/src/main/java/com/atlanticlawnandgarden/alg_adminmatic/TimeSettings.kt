package com.atlanticlawnandgarden.alg_adminmatic

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import android.widget.Toast

class TimeSettings: TimePickerDialog.OnTimeSetListener {

    var con: Context

    constructor(con: Context){
        this.con = con
    }



    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Toast.makeText(con,"Select hour: "+hourOfDay+" minute: "+minute,Toast.LENGTH_LONG).show()
    }


}