package com.aleksey_bakhtin.data.datasource.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.aleksey_bakhtin.data.datasource.local.entities.CourseFavouriteEntity


@Dao
interface CoursesDao {

    @Query("SELECT * FROM courses_favourite")
    fun getListFavourites(): List<CourseFavouriteEntity>

    @Insert
    fun addFavourite(courseEntity: CourseFavouriteEntity): Long

    @Delete
    fun removeFavourite(courseEntity: CourseFavouriteEntity): Int

}