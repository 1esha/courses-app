package com.aleksey_bakhtin.data

import android.content.Context
import androidx.room.Room
import com.aleksey_bakhtin.data.datasource.local.AppDatabase
import com.aleksey_bakhtin.data.datasource.local.entities.CourseFavouriteEntity
import com.aleksey_bakhtin.data.datasource.remote.models.CourseDataSourceModel
import com.aleksey_bakhtin.data.datasource.remote.CoursesApi
import com.aleksey_bakhtin.data.datasource.remote.RetrofitHelper
import com.aleksey_bakhtin.domain.CoursesRepository
import com.aleksey_bakhtin.domain.Result
import com.aleksey_bakhtin.domain.models.CourseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CoursesRepositoryImpl(context: Context) : CoursesRepository {

    private val coursesApi = RetrofitHelper.getInstance().create(CoursesApi::class.java)

    private val db = Room.databaseBuilder(
        context = context,
        klass = AppDatabase::class.java,
        name = NAME_DATABASE
    ).build()
    val coursesDao = db.CoursesDao()

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

    override fun getListFavourites(): Flow<Result> = flow{
        try {
            val listCoursesFavouriteEntity = coursesDao.getListFavourites()
            emit(
                Result.Success(
                   data = listCoursesFavouriteEntity.toListCourse()
                )
            )

        } catch (e: Exception){
            emit(Result.Error(exception = e))
        }

    }.flowOn(Dispatchers.IO)

    override fun addFavourite(courseModel: CourseModel): Flow<Result> = flow{
        try {
            val rowId = coursesDao.addFavourite(courseEntity = courseModel.toCourseFavouriteEntity())
            emit(
                Result.Success(
                    data = rowId
                )
            )

        } catch (e: Exception){
            emit(Result.Error(exception = e))
        }
    }.flowOn(Dispatchers.IO)

    override fun removeFavourite(courseModel: CourseModel): Flow<Result> = flow{
        try {
            val rows = coursesDao.removeFavourite(courseEntity = courseModel.toCourseFavouriteEntity())
            emit(
                Result.Success(
                    data = rows
                )
            )

        } catch (e: Exception){
            emit(Result.Error(exception = e))
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

    fun CourseFavouriteEntity.toCourseModel(): CourseModel {
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

    fun List<CourseFavouriteEntity>.toListCourse(): List<CourseModel> {
        val mutableListCourseModel = mutableListOf<CourseModel>()

        this.forEach {
            mutableListCourseModel.add(
                it.toCourseModel()
            )
        }

        return mutableListCourseModel
    }

    fun CourseModel.toCourseFavouriteEntity(): CourseFavouriteEntity {
        return CourseFavouriteEntity(
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

    companion object {
        private const val NAME_DATABASE = "app_database"
    }

}