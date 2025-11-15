package com.aleksey_bakhtin.data

import com.aleksey_bakhtin.data.datasource.models.CourseDataSourceModel
import com.aleksey_bakhtin.data.datasource.remote.CoursesApi
import com.aleksey_bakhtin.data.datasource.remote.RetrofitHelper
import com.aleksey_bakhtin.domain.CoursesRepository
import com.aleksey_bakhtin.domain.Result
import com.aleksey_bakhtin.domain.models.CourseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CoursesRepositoryImpl() : CoursesRepository {

    private val coursesApi = RetrofitHelper.getInstance().create(CoursesApi::class.java)

    override fun getListCourses(): Flow<Result> = flow {

        val response = coursesApi.getListCourses()

        if (response.isSuccessful) {
            val listCoursesDataSource = response.body()?.courses ?: emptyList()
            emit(
                Result.Success(
                    data = listCoursesDataSource.toListCourseModel()
                )
            )
        } else {
            emit(Result.Error(exception = Exception(response.message())))
        }

    }.flowOn(Dispatchers.IO)

    fun CourseDataSourceModel.toCourseModel(): CourseModel {
        return CourseModel(
            id = this.id,
            title = this.title,
            text = this.text,
            price = this.price,
            rate  = this.rate,
            startDate = this.startDate,
            hasLike = this.hasLike,
            publishDate = this.publishDate
        )
    }

    fun List<CourseDataSourceModel>.toListCourseModel(): List<CourseModel> {
        val mutableListCourseModel = mutableListOf<CourseModel>()

        this.forEach {
            mutableListCourseModel.add(
                it.toCourseModel()
            )
        }

        return mutableListCourseModel
    }

}