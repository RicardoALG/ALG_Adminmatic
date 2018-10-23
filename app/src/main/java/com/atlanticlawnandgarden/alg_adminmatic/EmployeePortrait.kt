package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_employee_portrait.*

class EmployeePortrait : AppCompatActivity() {





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_portrait)

        var extras = intent.extras
        var picPath = extras.get("picPath").toString()
        var name = extras.get("empName").toString()
        Log.d("url",picPath)

        if( extras != null){
            Picasso.get()
                    .load(picPath)
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder)
                    .into(employee_pic)

            tit_employee.setText(name)
        }

        btn_back.setOnClickListener(({
            finish()
        }))
    }
}
