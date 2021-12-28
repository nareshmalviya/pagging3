package com.codelab.pagging3.data.model

data class Locations(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residence: List<String>?
)