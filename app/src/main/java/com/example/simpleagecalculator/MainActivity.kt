package com.example.simpleagecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.SimpleAgeCalculator.R
import com.example.simpleagecalculator.model.AgeCalculatorModel
import java.util.*

class MainActivity : ComponentActivity() {

    private lateinit var etTodayDate: EditText
    private lateinit var etDateOfBirth: EditText
    private lateinit var btnClear: Button
    private lateinit var btnCalculate: Button
    private lateinit var tvYears: TextView
    private lateinit var tvMonths: TextView
    private lateinit var tvDays: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etTodayDate = findViewById(R.id.etTodayDate)
        etDateOfBirth = findViewById(R.id.etDateOfBirth)
        btnClear = findViewById(R.id.btnClear)
        btnCalculate = findViewById(R.id.btnCalculate)
        tvYears = findViewById(R.id.tvYears)
        tvMonths = findViewById(R.id.tvMonths)
        tvDays = findViewById(R.id.tvDays)

        etTodayDate.setOnClickListener {
            showDatePicker(etTodayDate)
        }

        etDateOfBirth.setOnClickListener {
            showDatePicker(etDateOfBirth)
        }

        btnClear.setOnClickListener {
            clearFields()
        }

        btnCalculate.setOnClickListener {
            calculateAge()
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

    private fun clearFields() {
        etTodayDate.text.clear()
        etDateOfBirth.text.clear()
        tvYears.text = "0"
        tvMonths.text = "0"
        tvDays.text = "0"
    }

    private fun calculateAge() {
        val todayDate = etTodayDate.text.toString()
        val dateOfBirth = etDateOfBirth.text.toString()

        if (todayDate.isNotEmpty() && dateOfBirth.isNotEmpty()) {
            val age = calculateAgeDifference(todayDate, dateOfBirth)
            displayAge(age)
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

    private fun displayAge(age: AgeCalculatorModel) {
        tvYears.text = age.years.toString()
        tvMonths.text = age.months.toString()
        tvDays.text = age.days.toString()
    }
}
