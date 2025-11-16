package com.aleksey_bakhtin.coursesapp.di


import com.aleksey_bakhtin.coursesapp.fragments.viewmodels.FavouritesViewModel
import com.aleksey_bakhtin.coursesapp.fragments.viewmodels.HomeViewModel
import com.aleksey_bakhtin.coursesapp.fragments.viewmodels.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel { SignInViewModel() }

    viewModel {
        HomeViewModel(
            getListCoursesUseCase = get(),
            getListFavouritesUseCase = get(),
            addFavouriteUseCase = get(),
            removeFavouriteUseCase = get()
        )
    }

    viewModel {
        FavouritesViewModel(
            getListFavouritesUseCase = get(),
            removeFavouriteUseCase = get()
        )
    }
}
