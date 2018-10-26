package com.atlanticlawnandgarden.alg_adminmatic

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import java.util.*

class TimeDialogHandler: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        var calendar: Calendar = Calendar.getInstance()
        var hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
        var min: Int = calendar.get(Calendar.MINUTE)
        var tdialog: TimePickerDialog
        //var ts: TimeSettings = TimeSettings(activity)
        //tdialog= TimePickerDialog(activity,ts,hour,min,DateFormat.is24HourFormat(activity))

        //return tdialog
    }


}