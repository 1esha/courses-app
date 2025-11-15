package com.aleksey_bakhtin.data.datasource.remote

import com.aleksey_bakhtin.data.datasource.models.DataWithCourses
import retrofit2.Response
import retrofit2.http.GET

interface CoursesApi {

    @GET("u/0/uc?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download")
    suspend fun getListCourses(): Response<DataWithCourses>

}


