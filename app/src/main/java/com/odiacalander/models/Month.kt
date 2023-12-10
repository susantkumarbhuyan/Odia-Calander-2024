package com.odiacalander.models

import com.odiacalander.models.entity.MonthImgsEntity

data class Month(val month: String, val year: Int, val monthId: Int, val image: String) {
    constructor() : this(
        "",
        2024,
        0,
        ""
    )
}
