package com.aleksey_bakhtin.domain.usecases

import com.aleksey_bakhtin.domain.CoursesRepository
import com.aleksey_bakhtin.domain.Result
import kotlinx.coroutines.flow.Flow

class GetListFavouritesUseCase(
    private val coursesRepository: CoursesRepository
) {

    fun execute(): Flow<Result> {
        val result = coursesRepository.getListFavourites()

        return  result
    }

}