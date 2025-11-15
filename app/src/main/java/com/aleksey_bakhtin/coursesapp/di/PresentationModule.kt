package com.aleksey_bakhtin.coursesapp.di


import com.aleksey_bakhtin.coursesapp.fragments.viewmodels.SignInViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val signInViewModelModule = module {
    viewModel { SignInViewModel() }
}