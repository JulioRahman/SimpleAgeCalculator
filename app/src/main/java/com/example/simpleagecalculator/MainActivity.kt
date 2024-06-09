package com.example.simpleagecalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import com.example.SimpleAgeCalculator.R
import com.example.simpleagecalculator.model.Age
import com.example.simpleagecalculator.viewmodel.AgeViewModel
import java.util.*

class MainActivity : ComponentActivity() {

    private val viewModel: AgeViewModel by viewModels()

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

        observeAge()
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
            viewModel.calculateAge(todayDate, dateOfBirth)
        }
    }

    private fun observeAge() {
        viewModel.age.observe(this) { age ->
            displayAge(age)
        }
    }

    private fun displayAge(age: Age) {
        tvYears.text = age.years.toString()
        tvMonths.text = age.months.toString()
        tvDays.text = age.days.toString()
    }
}
