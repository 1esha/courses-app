package com.aleksey_bakhtin.coursesapp.di

import com.aleksey_bakhtin.domain.usecases.GetListCoursesUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetListCoursesUseCase> {
        GetListCoursesUseCase(coursesRepository = get())
    }

}