package com.aleksey_bakhtin.coursesapp.utils

import android.text.InputFilter
import android.text.Spanned

class EmailMask(private val callback:(String) -> Unit): InputFilter{

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val currentText = dest?.toString() ?: ""
        val newText = currentText.substring(0, dstart) + source + currentText.substring(dend)

        callback(newText)

        return source

    }

}