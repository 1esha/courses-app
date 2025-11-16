package com.aleksey_bakhtin.coursesapp.fragments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksey_bakhtin.domain.Result
import com.aleksey_bakhtin.domain.asSuccess
import com.aleksey_bakhtin.domain.models.CourseModel
import com.aleksey_bakhtin.domain.usecases.GetListCoursesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val getListCoursesUseCase: GetListCoursesUseCase): ViewModel() {

    private val _stateScreen: MutableStateFlow<Result> = MutableStateFlow<Result>(Result.Loading)
    val stateScreen = _stateScreen.asStateFlow()

    private var isLaunch = true
    var listCourses = listOf<CourseModel>()

    private fun onError(exception: Exception){
        _stateScreen.update { Result.Error(exception = exception) }
    }

    fun getCourses() {
        viewModelScope.launch {
            try {
                if (!isLaunch) return@launch

                getListCoursesUseCase.execute().collect { result ->
                    if (result is Result.Error) {
                        onError(exception = result.exception)
                        return@collect
                    }

                    val data = result.asSuccess()?.data ?: throw NullPointerException()

                    val list = data as List<*>
                    val mutableListCourses = mutableListOf<CourseModel>()

                    list.forEach { course ->
                        course as CourseModel
                        mutableListCourses.add(course)
                    }

                    listCourses = mutableListCourses

                    _stateScreen.update { Result.Success(data = listCourses) }

                    isLaunch = false
                }
            } catch (e: Exception){
                onError(exception = e)
            }

        }
    }
}