package com.odiacalander.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.odiacalander.entity.MonthDetailsEntity
import com.odiacalander.entity.MonthImgsEntity

@Dao
interface CalendarDao {
    @Insert
    suspend fun storeMonthList(monthList: List<MonthImgsEntity>)

    @Insert
    suspend fun storeMonthDetails(monthDetails: List<MonthDetailsEntity>)

    @Query("DELETE FROM monthImgData")
    suspend fun deleteAllMonthImg()

    @Query("DELETE FROM monthDetails")
    suspend fun deleteAllMonthDetails()

    @Query("SELECT * FROM monthImgData")
    suspend fun getMonthList(): List<MonthImgsEntity>
    @Query("SELECT * FROM monthDetails WHERE id= :id")
    suspend fun getMonthData(id: Int): MonthDetailsEntity
    @Query("SELECT COUNT(id) FROM monthImgData")
    suspend fun countMonthList(): Int
    @Query("SELECT COUNT(id) FROM monthDetails")
    suspend fun countMonthData(): Int
}