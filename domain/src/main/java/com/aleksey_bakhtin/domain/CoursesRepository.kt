package com.aleksey_bakhtin.domain

import com.aleksey_bakhtin.domain.models.CourseModel
import kotlinx.coroutines.flow.Flow

interface CoursesRepository {

    fun getListCourses(): Flow<Result>

    fun getListFavourites(): Flow<Result>

    fun addFavourite(courseModel: CourseModel): Flow<Result>

    fun removeFavourite(courseModel: CourseModel): Flow<Result>

}