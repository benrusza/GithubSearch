package com.brz.app.retrofit

import com.brz.app.model.ResponseGetRepos
import com.brz.app.model.ResponseSearchUsers
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {

    @GET("search/users")
    fun getUsers(
        @Query("q") text : String
    ) : Call<ResponseSearchUsers>

    @GET("users/{user}/repos")
    fun getRepos(
        @Path("user") user : String
    ) : Call<ResponseGetRepos>

}