package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Context

class SharedPreferences (context: Context) {

    val PREFERENCES = "SharedPreferences"
    val PREFERENCE_EMPLOYEE = "Employee"
    val PREFERENCE_LOG_STATUS = "false"
    val PREFERENCE_EMPLOYEE_LEVEL = "0"

    val preferences = context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE)

    fun getEmployeeName(): String{
        return preferences.getString(PREFERENCE_EMPLOYEE,"Employee")
    }

    fun setEmployeeName(employee:String){
        val editor = preferences.edit()
        editor.putString(PREFERENCE_EMPLOYEE,employee)
        editor.apply()
    }

    fun getLogStatus():Boolean{
        return  preferences.getBoolean(PREFERENCE_LOG_STATUS,false)
    }

    fun setLogStatus(logStatus:Boolean){
        val editor = preferences.edit()
        editor.putBoolean(PREFERENCE_LOG_STATUS,logStatus)
        editor.apply()
    }

    fun getEmployeeLevel(): Int{

        return preferences.getInt(PREFERENCE_EMPLOYEE_LEVEL,0)
    }

    fun setEmployeeLevel(level:Int){
        val editor = preferences.edit()
        editor.putInt(PREFERENCE_EMPLOYEE_LEVEL,level)
        editor.apply()
    }



}