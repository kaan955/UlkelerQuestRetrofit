package com.ulkeler.titanium.kaanb.ulkeler

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("rest/v2/all")
    fun tumVerileriGetir():Call<List<UlkeBilgileri>>

}