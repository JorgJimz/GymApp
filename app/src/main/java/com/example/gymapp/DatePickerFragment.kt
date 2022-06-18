package com.example.gymapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.time.DayOfWeek
import java.util.*

class DatePickerFragment(val listener : (day:Int, month:Int, year:Int) -> Unit) : DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c : Calendar = Calendar.getInstance()
        val day : Int = c.get(Calendar.DAY_OF_MONTH)
        val month : Int = c.get(Calendar.MONTH)
        val year : Int = c.get(Calendar.YEAR)
        return DatePickerDialog(activity as Context, this, year, month, day)
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year)
    }
}