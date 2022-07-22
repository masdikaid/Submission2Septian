package com.dicoding.githubusersubmission2.api

import com.dicoding.githubusersubmission2.networking.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users{username}")
    @Headers("Authorization: token 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")
            fun searchUser(
                @Query("q") login: String
            ): Call<UserResponse>

            @GET("users/{login}")
            @Headers("Authorization: token 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")
            fun getUserDetail(
                @Path("login") login: String
            ): Call<DetailResponse>

            @GET("users/{login}/following")
            @Headers("Authorization: token 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")
            fun getUserFollowing(
                @Path("login") login: String
            ): Call<List<GithubUser>>

            @GET("users/{username}/followers")
            @Headers("Authorization: token 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")
            fun getUserFollowers(
                @Path("login") login: String
            ): Call<List<GithubUser>>

}