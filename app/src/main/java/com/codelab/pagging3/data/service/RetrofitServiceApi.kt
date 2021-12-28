package com.codelab.pagging3.data.service

import com.codelab.pagging3.data.model.Characters
import com.codelab.pagging3.data.model.Episode
import com.codelab.pagging3.data.model.Locations
import com.codelab.pagging3.data.model.PagedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServiceApi {
    @GET("character/")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<PagedResponse<Characters>>

    @GET("episode/")
    suspend fun getAllEpisodes(@Query("page") page: Int): Response<PagedResponse<Episode>>

    @GET("location/")
    suspend fun getAllLocations(@Query("page") page: Int): Response<PagedResponse<Locations>>
}