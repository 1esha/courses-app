package com.aleksey_bakhtin.domain

import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    fun getListCourses(): Flow<Result>

}