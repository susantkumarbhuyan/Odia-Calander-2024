package com.odiacalander.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.odiacalander.models.entity.MonthDetailsEntity
import com.odiacalander.models.entity.MonthImgsEntity

@Dao
interface CalendarDao {
    @Insert
    suspend fun storeMonthList(monthList: List<MonthImgsEntity>)

//    @Insert
//    suspend fun storeMonthDetails(monthDetails: List<MonthDetailsEntity>)
    @Upsert
    suspend fun storeMonthDetails(monthDetails: List<MonthDetailsEntity>)

    @Query("DELETE FROM monthImgData")
    suspend fun deleteAllMonthImg()

    @Query("DELETE FROM monthDetails")
    suspend fun deleteAllMonthDetails()

    @Query("SELECT * FROM monthImgData")
    suspend fun getMonthList(): List<MonthImgsEntity>

    @Query("SELECT * FROM monthImgData WHERE id = :id")
    suspend fun getMonthImgs(id: Int): MonthImgsEntity

    @Query("SELECT * FROM monthDetails WHERE id = :id")
    suspend fun getMonthData(id: Int): MonthDetailsEntity

    @Query("SELECT COUNT(id) FROM monthImgData")
    suspend fun countMonthList(): Int

    @Query("SELECT COUNT(id) FROM monthDetails")
    suspend fun countMonthData(): Int

    @Query("SELECT *  FROM monthDetails WHERE year = :year AND langId = :langId")
    suspend fun getYearlyFestival(year: Int, langId: Int): List<MonthDetailsEntity>

}