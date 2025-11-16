package com.aleksey_bakhtin.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aleksey_bakhtin.data.datasource.local.entities.CourseFavouriteEntity

@Database(entities = [CourseFavouriteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun CoursesDao(): CoursesDao

}