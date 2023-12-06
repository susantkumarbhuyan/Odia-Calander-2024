package com.odiacalander.models

import com.odiacalander.data.db.entity.MonthImgsEntity

data class Month(val month: String, val year: Int, val monthId: Int, val image: String) {
    constructor(monthImgsEntity: MonthImgsEntity) : this(
        monthImgsEntity.month,
        monthImgsEntity.year,
        monthImgsEntity.monthId,
        monthImgsEntity.image
    )
}
