package com.savr.mvppattern.data

import com.savr.mvppattern.model.ResponseKodePos
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("k69.json")
    fun getData (): Flowable<Response<List<ResponseKodePos>>>
}