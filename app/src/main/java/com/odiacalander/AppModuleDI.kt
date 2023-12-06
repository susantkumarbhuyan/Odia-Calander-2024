package com.odiacalander

import android.content.Context
import androidx.room.Room
import com.odiacalander.data.db.CalendarDatabase
import com.odiacalander.data.retrofit.retrofit.ApiConstants
import com.odiacalander.data.retrofit.retrofit.BloggerApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModuleDI {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideBloggerApiService(retrofit: Retrofit): BloggerApiService =
        retrofit.create(BloggerApiService::class.java)


    @Provides
    @Singleton
    fun provideCalendarDatabase(@ApplicationContext context: Context): CalendarDatabase =
        Room.databaseBuilder(
            context,
            CalendarDatabase::class.java,
            "calendarDB"
        ).fallbackToDestructiveMigration()
            .build()


}