package com.aleksey_bakhtin.domain.usecases

import com.aleksey_bakhtin.domain.CoursesRepository
import com.aleksey_bakhtin.domain.Result
import kotlinx.coroutines.flow.Flow

/**
 * UseCase для получения списка курсов
 */
class GetListCoursesUseCase(
    private val coursesRepository: CoursesRepository
) {

    fun execute(): Flow<Result> {
        val result = coursesRepository.getListCourses()

        return result
    }

}