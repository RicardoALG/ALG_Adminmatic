package com.atlanticlawnandgarden.alg_adminmatic

import retrofit2.Call
import retrofit2.http.GET

interface ItemRequestInterface {

    @get:GET("android/jsonandroid")
    val json: Call<JSONResponse>
}