package com.atlanticlawnandgarden.alg_adminmatic

import android.app.DatePickerDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_schedule_settings.*
import java.util.*
import kotlin.collections.ArrayList

class ScheduleSettings : AppCompatActivity() {

    lateinit var option : Spinner
    lateinit var result : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_settings)

        //Calendar

        val c= Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        recyclerView.visibility= View.INVISIBLE



        btn_start_date.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view: DatePicker, mYear , mMonth , mDay ->

            txt_start_date.setText(""+ mMonth +"/"+ mDay + "/" + mYear )},year,month,day)

            dpd.show()
        }

        btn_stop_date.setOnClickListener{
            val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view: DatePicker, mYear , mMonth , mDay ->

                txt_stop_date.setText(""+ mMonth +"/"+ mDay + "/" + mYear )},year,month,day)

            dpd.show()
        }

        mowing.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                plowing.isChecked = false
            }


        }

        /////////////SHOW/HIDE DEPTH

            plowing.setOnCheckedChangeListener { buttonView, isChecked ->

                if(isChecked){
                    recyclerView.visibility= View.VISIBLE
                } else {
                    recyclerView.visibility= View.INVISIBLE
                }


            }




        ////////////RECYCLER VIEW

        val users: ArrayList<String> = ArrayList()
        users.addAll(listOf("All Depths","1 Inch","2 Inches","3 Inches","4 Inches","5 Inches","6 Inches","7 Inches","8 Inches","9 Inches","10 Inches","11 Inches","12 Inches"))


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = UsersAdapter(users)




        ////////////DEPTH SPINNER

/*        option = findViewById(R.id.spinner_plow) as Spinner
        result = findViewById(R.id.txt_depths) as TextView

        val options = arrayOf("All Depths", "1 Inch","2 Inches" , "3 Inches", "4 Inches", "5 Inches", "6 Inches", "7 Inches", "8 Inches", "9 Inches", "10 Inches", "11 Inches", "12 Inches")

        option.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options)

        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "Dame craneo"
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                result.text = options.get(position)
            }

        }*/

    }
}
