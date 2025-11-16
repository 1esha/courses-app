package com.aleksey_bakhtin.coursesapp.utils

import androidx.fragment.app.Fragment
import com.aleksey_bakhtin.domain.Result

interface StateScreen {

    fun setStateScreen(result: Result)

}

fun Fragment.getStateScreen(): StateScreen {
    return this.requireActivity() as StateScreen
}