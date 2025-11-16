package com.aleksey_bakhtin.coursesapp.utils

import com.aleksey_bakhtin.domain.models.CourseModel
import java.text.SimpleDateFormat
import java.util.Locale


class DateHelper() {

    fun getFormatDate(date: String): String {

        val year = date.take(4)
        val day = date.drop(8)
        val month = date.substring(5, 7)

        var currentNameMonth = ""
        Month.entries.forEach {
            if (it.number == month) {
                currentNameMonth = it.nameMonth
            }
        }
        return "$day $currentNameMonth $year"
    }

    fun sortByDate(mutableList: MutableList<CourseModel>){
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        mutableList.sortWith(Comparator { d1, d2 ->
            compareDates(sdf, d1.publishDate, d2.publishDate)
        })
    }

    private fun compareDates(sdf: SimpleDateFormat, d1: String, d2: String): Int {
        return try {
            sdf.parse(d2)?.compareTo(sdf.parse(d1)) ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    enum class Month(val nameMonth: String, val number: String) {
        JANUARY("Января", "01"),
        FEBRUARY("Февраля", "02"),
        MARCH("Марта", "03"),
        APRIL("Апреля", "04"),
        MAY("Мая", "05"),
        JUNE("Июня", "06"),
        JULY("Июля", "07"),
        AUGUST("Августа", "08"),
        SEPTEMBER("Сентября", "09"),
        OCTOBER("Октября", "10"),
        NOVEMBER("Ноября", "11"),
        DECEMBER("Декабря", "12")
    }
}

