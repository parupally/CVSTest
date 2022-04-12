package com.example.cvstest

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {

    @GET("photos_public.gne?format=json&nojsoncallback=1&tags=porcupine")
    suspend fun getAllImages(): Response<ImageResponse>
}