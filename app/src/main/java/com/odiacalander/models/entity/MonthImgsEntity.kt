package com.odiacalander.models.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.odiacalander.models.Month

@Entity(tableName = "monthImgData")
@Keep
data class MonthImgsEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val monthId: Int, val month: String, val year: Int, val image: String
) {

    fun toMonth(): Month {
        return Month(
            monthId = monthId,
            month = month,
            year = year,
            image = image
        )
    }
}