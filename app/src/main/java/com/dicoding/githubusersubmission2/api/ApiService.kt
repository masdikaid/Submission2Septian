package com.dicoding.githubusersubmission2.api

import com.dicoding.githubusersubmission2.networking.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
            fun searchUser(
                @Query("q") username: String
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