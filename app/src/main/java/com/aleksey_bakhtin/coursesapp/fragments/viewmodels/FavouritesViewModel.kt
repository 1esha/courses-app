package com.aleksey_bakhtin.coursesapp.fragments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aleksey_bakhtin.domain.Result
import com.aleksey_bakhtin.domain.asSuccess
import com.aleksey_bakhtin.domain.models.CourseModel
import com.aleksey_bakhtin.domain.usecases.GetListFavouritesUseCase
import com.aleksey_bakhtin.domain.usecases.RemoveFavouriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val getListFavouritesUseCase: GetListFavouritesUseCase,
    private val removeFavouriteUseCase: RemoveFavouriteUseCase
): ViewModel() {

    private val _stateScreen: MutableStateFlow<Result> = MutableStateFlow<Result>(Result.Loading)
    val stateScreen = _stateScreen.asStateFlow()

    private var isLaunchFavourites = true

    private var isInstallUi = true

    var listFavourites = listOf<CourseModel>()

    private fun onLoading(){
        _stateScreen.update { Result.Loading }
    }

    private fun onError(exception: Exception){
        _stateScreen.update { Result.Error(exception = exception) }
    }

    fun installListFavourites(block:() -> Unit){
        if (isInstallUi) block()

        isInstallUi = false
    }

    fun setInstallUi(){
        isInstallUi = true

    }

    fun getFavourites(){
        viewModelScope.launch {
            try {
                if (!isLaunchFavourites) return@launch

                onLoading()

                getListFavouritesUseCase.execute().collect { result ->
                    if (result is Result.Error) {
                        onError(exception = result.exception)
                        return@collect
                    }

                    val data = result.asSuccess()?.data ?: throw NullPointerException()
                    val list = data as List<*>
                    val mutableListFavourites = mutableListOf<CourseModel>()

                    list.forEach { course ->
                        course as CourseModel
                        mutableListFavourites.add(course)
                    }

                    listFavourites = mutableListFavourites

                    _stateScreen.update {
                        Result.Success(data = Pair(TYPE_GET_FAVOURITES,listFavourites))
                    }

                    isLaunchFavourites = false
                }

            } catch (e: Exception){
                onError(exception = e)
            }
        }
    }

    fun removeFavourite(course: CourseModel) {
        viewModelScope.launch {

            onLoading()

            removeFavouriteUseCase.execute(courseModel = course).collect { result ->
                if (result is Result.Error) {
                    onError(exception = result.exception)
                    return@collect
                }

                val data = result.asSuccess()?.data ?: throw NullPointerException()
                val rows = data as Int

                _stateScreen.update {
                    Result.Success(data = Pair(TYPE_REMOVE_FAVOURITE,rows))
                }
            }
        }
    }

    companion object {
        const val TYPE_GET_FAVOURITES = "getFavourites"
        const val TYPE_REMOVE_FAVOURITE = "removeFavourite"
    }
}