package com.aleksey_bakhtin.coursesapp.di

import com.aleksey_bakhtin.data.CoursesRepositoryImpl
import com.aleksey_bakhtin.domain.CoursesRepository
import org.koin.dsl.module

val dataModule = module {

    single<CoursesRepository> {
        CoursesRepositoryImpl(context = get())
    }

}