package com.aleksey_bakhtin.coursesapp.di

import com.aleksey_bakhtin.domain.usecases.AddFavouriteUseCase
import com.aleksey_bakhtin.domain.usecases.GetListCoursesUseCase
import com.aleksey_bakhtin.domain.usecases.GetListFavouritesUseCase
import com.aleksey_bakhtin.domain.usecases.RemoveFavouriteUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetListCoursesUseCase> {
        GetListCoursesUseCase(coursesRepository = get())
    }

    factory<GetListFavouritesUseCase> {
        GetListFavouritesUseCase(coursesRepository = get())
    }

    factory<AddFavouriteUseCase> {
        AddFavouriteUseCase(coursesRepository = get())
    }

    factory<RemoveFavouriteUseCase> {
        RemoveFavouriteUseCase(coursesRepository = get())
    }

}