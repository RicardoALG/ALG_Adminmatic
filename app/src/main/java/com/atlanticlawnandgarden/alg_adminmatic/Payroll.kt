package com.atlanticlawnandgarden.alg_adminmatic

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.dynamic.SupportFragmentWrapper

class Payroll : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payroll)
    }
    fun showDialog(v: View){

        var tdh: TimeDialogHandler = TimeDialogHandler()
        tdh.show(supportFragmentManager,"time picker")

    }
}
