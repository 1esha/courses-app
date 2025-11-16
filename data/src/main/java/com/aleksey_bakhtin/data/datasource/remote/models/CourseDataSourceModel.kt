package com.aleksey_bakhtin.data.datasource.remote.models

data class CourseDataSourceModel(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: Double,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)
