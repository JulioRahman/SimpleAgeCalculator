package com.example.simpleagecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.SimpleAgeCalculator.R
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etTodayDate: EditText = findViewById(R.id.etTodayDate)
        val etDateOfBirth: EditText = findViewById(R.id.etDateOfBirth)
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnCalculate: Button = findViewById(R.id.btnCalculate)
        val tvYears: TextView = findViewById(R.id.tvYears)
        val tvMonths: TextView = findViewById(R.id.tvMonths)
        val tvDays: TextView = findViewById(R.id.tvDays)

        etTodayDate.setOnClickListener {
            showDatePicker(etTodayDate)
        }

        etDateOfBirth.setOnClickListener {
            showDatePicker(etDateOfBirth)
        }

        btnClear.setOnClickListener {
            clearFields(etTodayDate, etDateOfBirth, tvYears, tvMonths, tvDays)
        }

        btnCalculate.setOnClickListener {
            calculateAge(etTodayDate, etDateOfBirth, tvYears, tvMonths, tvDays)
        }
    }

    private fun showDatePicker(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                editText.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun clearFields(etTodayDate: EditText, etDateOfBirth: EditText, tvYears: TextView, tvMonths: TextView, tvDays: TextView) {
        etTodayDate.text.clear()
        etDateOfBirth.text.clear()
        tvYears.text = "0"
        tvMonths.text = "0"
        tvDays.text = "0"
    }

    private fun calculateAge(etTodayDate: EditText, etDateOfBirth: EditText, tvYears: TextView, tvMonths: TextView, tvDays: TextView) {
        val todayDate = etTodayDate.text.toString()
        val dateOfBirth = etDateOfBirth.text.toString()

        if (todayDate.isNotEmpty() && dateOfBirth.isNotEmpty()) {
            val age = calculateAgeDifference(todayDate, dateOfBirth)
            displayAge(age, tvYears, tvMonths, tvDays)
        }
    }

    private fun calculateAgeDifference(todayDate: String, dateOfBirth: String): AgeCalculatorModel {
        val today = Calendar.getInstance()
        val birthDate = Calendar.getInstance()

        val todayParts = todayDate.split("/")
        val birthParts = dateOfBirth.split("/")

        today.set(todayParts[2].toInt(), todayParts[1].toInt() - 1, todayParts[0].toInt())
        birthDate.set(birthParts[2].toInt(), birthParts[1].toInt() - 1, birthParts[0].toInt())

        var years = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        var months = today.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH)
        var days = today.get(Calendar.DAY_OF_MONTH) - birthDate.get(Calendar.DAY_OF_MONTH)

        if (days < 0) {
            months--
            days += today.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        if (months < 0) {
            years--
            months += 12
        }

        return AgeCalculatorModel(years, months, days)
    }

    private fun displayAge(age: AgeCalculatorModel, tvYears: TextView, tvMonths: TextView, tvDays: TextView) {
        tvYears.text = age.years.toString()
        tvMonths.text = age.months.toString()
        tvDays.text = age.days.toString()
    }
}
