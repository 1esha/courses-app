package com.aleksey_bakhtin.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses_favourite")
data class CourseFavouriteEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    @ColumnInfo(name = "has_like")
    val hasLike: Boolean,
    @ColumnInfo(name = "publish_date")
    val publishDate: String
)
