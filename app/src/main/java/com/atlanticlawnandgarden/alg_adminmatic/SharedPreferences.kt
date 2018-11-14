package com.atlanticlawnandgarden.alg_adminmatic

import android.content.Context

class SharedPreferences (context: Context) {

    val PREFERENCES = "SharedPreferences"
    val PREFERENCE_EMPLOYEE_ID = "ID"
    val PREFERENCE_EMPLOYEE = "Employee"
    val PREFERENCE_EMPLOYEE_FNAME = "First Name"
    val PREFERENCE_EMPLOYEE_LNAME = "Last Name"

    val PREFERENCE_LOG_STATUS = "false"
    val PREFERENCE_EMPLOYEE_LEVEL = "0"

    val PREFERENCE_PAYROLL_ACTIVE = false
    val PREFERENCE_PR_START = ""
    val PREFERENCE_PR_STOP = ""
    val PREFERENCE_PR_LUNCH = ""

    val preferences = context.getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE)



    fun getEmployeeID(): String{
        return preferences.getString(PREFERENCE_EMPLOYEE_ID,"0")
    }

    fun setEmployeeID(id:String){
        val editor = preferences.edit()
        editor.putString(PREFERENCE_EMPLOYEE_ID,id)
        editor.apply()
    }

    fun getEmployeeFName(): String{
        return preferences.getString(PREFERENCE_EMPLOYEE_FNAME,"Employee")
    }

    fun setEmployeeFName(employeeFname:String){
        val editor = preferences.edit()
        editor.putString(PREFERENCE_EMPLOYEE_FNAME,employeeFname)
        editor.apply()
    }
    fun getEmployeeLName(): String{
        return preferences.getString(PREFERENCE_EMPLOYEE_LNAME,"Employee")
    }

    fun setEmployeeLName(employeeLname:String){
        val editor = preferences.edit()
        editor.putString(PREFERENCE_EMPLOYEE_LNAME,employeeLname)
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