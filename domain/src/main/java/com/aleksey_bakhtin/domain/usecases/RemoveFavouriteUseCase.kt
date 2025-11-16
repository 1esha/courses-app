package com.aleksey_bakhtin.domain.usecases

import com.aleksey_bakhtin.domain.CoursesRepository
import com.aleksey_bakhtin.domain.Result
import com.aleksey_bakhtin.domain.models.CourseModel
import kotlinx.coroutines.flow.Flow

class RemoveFavouriteUseCase(private val coursesRepository: CoursesRepository) {

    fun execute(courseModel: CourseModel): Flow<Result> {
        val result = coursesRepository.removeFavourite(courseModel = courseModel)

        return  result
    }

}