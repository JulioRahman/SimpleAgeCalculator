package com.example.simpleagecalculator.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpleagecalculator.model.Age
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class AgeViewModel : ViewModel() {

    private val _age = MutableLiveData<Age>()
    val age: LiveData<Age> = _age

    fun calculateAge(todayDate: String, dateOfBirth: String) {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        val today = LocalDate.parse(todayDate, formatter)
        val birthDate = LocalDate.parse(dateOfBirth, formatter)
        val period = Period.between(birthDate, today)

        _age.value = Age(period.years, period.months, period.days)
    }
}
