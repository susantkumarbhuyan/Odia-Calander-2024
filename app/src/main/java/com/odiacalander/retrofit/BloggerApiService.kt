package com.odiacalander.retrofit

import com.odiacalander.dataclasses.BlogPage
import com.odiacalander.retrofit.ApiConstants.MONTH_DETAILS_BLOG_URL
import com.odiacalander.retrofit.ApiConstants.MONTH_IMAGE_BLOG_URL
import retrofit2.Response
import retrofit2.http.GET

interface BloggerApiService {
    @GET(MONTH_IMAGE_BLOG_URL)
    suspend fun fetchMonthList() : Response<BlogPage>
    @GET(MONTH_DETAILS_BLOG_URL)
    suspend fun fetchMonthDetails() : Response<BlogPage>
}