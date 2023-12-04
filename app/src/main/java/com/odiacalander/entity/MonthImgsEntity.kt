package com.odiacalander.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monthImgData")
data class MonthImgsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val monthId: Int, val month: String, val year: Int, val image: String
)