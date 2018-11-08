//package com.atlanticlawnandgarden.alg_adminmatic
//
//import java.lang.reflect.AccessibleObject.setAccessible
//import android.widget.TimePicker
//import android.os.Build
//import android.app.TimePickerDialog
//import android.content.res.TypedArray
//import jdk.nashorn.internal.objects.NativeDate.getTime
//import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy.SHORT
//import android.app.TimePickerDialog.OnTimeSetListener
//import android.content.Context
//import java.text.DateFormat
//import java.util.*
//import android.widget.DatePicker
//import android.app.DatePickerDialog
//
//
//
//
//class RangeTimePickerDialog {
//
//    private var minHour = -1
//    private var minMinute = -1
//
//    private var maxHour = 25
//    private var maxMinute = 25
//
//    private var currentHour = 0
//    private var currentMinute = 0
//
//    private val calendar = Calendar.getInstance()
//    private var dateFormat: DateFormat
//
//    init{
//        btnDate.setOnClickListener(object : View.OnClickListener() {
//            fun onClick(v: View) {
//
//                val datePickerListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> }
//
//                val c = Calendar.getInstance()
//                val mYear = c.get(Calendar.YEAR)
//                val mMonth = c.get(Calendar.MONTH)
//                val mDay = c.get(Calendar.DAY_OF_MONTH)
//
//
//                val datePickerDialog = DatePickerDialog(
//                        this@Main23Activity, datePickerListener,
//                        mYear, mMonth, mDay)
//                val datePicker = datePickerDialog.datePicker
//
//                c.add(Calendar.MONTH, +1)
//                val oneMonthAhead = c.timeInMillis
//                datePicker.maxDate = oneMonthAhead
//                datePicker.minDate = System.currentTimeMillis() - 1000
//                datePickerDialog.show()
//
//            }
//        })
//
//        btnTime.setOnClickListener(object : View.OnClickListener() {
//            fun onClick(v: View) {
//
//                val timePickerListener = OnTimeSetListener { view, hour, minute -> }
//                val c = Calendar.getInstance()
//
//                val timePickerDialog = TimePickerDialog(this@Main23Activity, timePickerListener,
//                        c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE) + 5, false)
//                timePickerDialog.show()
//
//            }
//        })
//    }
//
//
//    fun RangeTimePickerDialog(context: Context, dialogTheme: Int, callBack: OnTimeSetListener, hourOfDay: Int, minute: Int, is24HourView: Boolean): ??? {
//        super(context, callBack, hourOfDay, minute, is24HourView)
//        currentHour = hourOfDay
//        currentMinute = minute
//        dateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
//        fixSpinner(context, hourOfDay, minute, is24HourView)
//
//        try {
//            val superclass = javaClass.superclass
//            val mTimePickerField = superclass!!.getDeclaredField("mTimePicker")
//            mTimePickerField.isAccessible = true
//            val mTimePicker = mTimePickerField.get(this) as TimePicker
//            mTimePicker.setOnTimeChangedListener(this)
//        } catch (e: NoSuchFieldException) {
//        } catch (e: IllegalArgumentException) {
//        } catch (e: IllegalAccessException) {
//        }
//
//    }
//
//    fun setMin(hour: Int, minute: Int) {
//        minHour = hour
//        minMinute = minute
//    }
//
//    fun setMax(hour: Int, minute: Int) {
//        maxHour = hour
//        maxMinute = minute
//    }
//
//    fun onTimeChanged(view: TimePicker, hourOfDay: Int, minute: Int) {
//
//        var validTime = true
//        if (hourOfDay < minHour || hourOfDay == minHour && minute < minMinute) {
//            validTime = false
//        }
//
//        if (hourOfDay > maxHour || hourOfDay == maxHour && minute > maxMinute) {
//            validTime = false
//        }
//
//        if (validTime) {
//            currentHour = hourOfDay
//            currentMinute = minute
//        }
//
//        updateTime(currentHour, currentMinute)
//        updateDialogTitle(view, currentHour, currentMinute)
//    }
//
//    private fun updateDialogTitle(timePicker: TimePicker, hourOfDay: Int, minute: Int) {
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
//        calendar.set(Calendar.MINUTE, minute)
//        val title = dateFormat.format(calendar.getTime())
//        setTitle(title)
//    }
//
//
//    private fun fixSpinner(context: Context, hourOfDay: Int, minute: Int, is24HourView: Boolean) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // android:timePickerMode spinner and clock began in Lollipop
//            try {
//                // Get the theme's android:timePickerMode
//                //two modes are available clock mode and spinner mode ... selecting spinner mode for latest versions
//                val MODE_SPINNER = 2
//                val styleableClass = Class.forName("com.android.internal.R\$styleable")
//                val timePickerStyleableField = styleableClass.getField("TimePicker")
//                val timePickerStyleable = timePickerStyleableField.get(null) as IntArray
//                val a = context.obtainStyledAttributes(null, timePickerStyleable, android.R.attr.timePickerStyle, 0)
//                val timePickerModeStyleableField = styleableClass.getField("TimePicker_timePickerMode")
//                val timePickerModeStyleable = timePickerModeStyleableField.getInt(null)
//                val mode = a.getInt(timePickerModeStyleable, MODE_SPINNER)
//                a.recycle()
//                if (mode == MODE_SPINNER) {
//                    val timePicker = findField(TimePickerDialog::class.java, TimePicker::class.java, "mTimePicker")!!.get(this) as TimePicker
//                    val delegateClass = Class.forName("android.widget.TimePicker\$TimePickerDelegate")
//                    val delegateField = findField(TimePicker::class.java, delegateClass, "mDelegate")
//                    var delegate = delegateField!!.get(timePicker)
//                    val spinnerDelegateClass: Class<*>
//                    if (Build.VERSION.SDK_INT != Build.VERSION_CODES.LOLLIPOP) {
//                        spinnerDelegateClass = Class.forName("android.widget.TimePickerSpinnerDelegate")
//                    } else {
//
//                        spinnerDelegateClass = Class.forName("android.widget.TimePickerClockDelegate")
//                    }
//                    if (delegate.javaClass != spinnerDelegateClass) {
//                        delegateField!!.set(timePicker, null) // throw out the TimePickerClockDelegate!
//                        timePicker.removeAllViews() // remove the TimePickerClockDelegate views
//                        val spinnerDelegateConstructor = spinnerDelegateClass.getConstructor(TimePicker::class.java, Context::class.java, AttributeSet::class.java, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
//                        spinnerDelegateConstructor.isAccessible = true
//                        // Instantiate a TimePickerSpinnerDelegate
//                        delegate = spinnerDelegateConstructor.newInstance(timePicker, context, null, android.R.attr.timePickerStyle, 0)
//                        delegateField!!.set(timePicker, delegate) // set the TimePicker.mDelegate to the spinner delegate
//                        // Set up the TimePicker again, with the TimePickerSpinnerDelegate
//                        timePicker.setIs24HourView(is24HourView)
//                        timePicker.currentHour = hourOfDay
//                        timePicker.currentMinute = minute
//                        timePicker.setOnTimeChangedListener(this)
//                    }
//                }
//            } catch (e: Exception) {
//                throw RuntimeException(e)
//            }
//
//        }
//    }
//
//    private fun findField(objectClass: Class<*>, fieldClass: Class<*>, expectedName: String): Field? {
//        try {
//            val field = objectClass.getDeclaredField(expectedName)
//            field.isAccessible = true
//            return field
//        } catch (e: NoSuchFieldException) {
//        }
//        // ignore
//        // search for it if it wasn't found under the expected ivar name
//        for (searchField in objectClass.declaredFields) {
//            if (searchField.type === fieldClass) {
//                searchField.isAccessible = true
//                return searchField
//            }
//        }
//        return null
//    }
//
//
//}