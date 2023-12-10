package com.odiacalander.models

import androidx.annotation.Keep


@Keep
data class BlogPage(
    val kind: String,
    val id: String,
    val url: String,
    val title: String,
    val content: String,
)


