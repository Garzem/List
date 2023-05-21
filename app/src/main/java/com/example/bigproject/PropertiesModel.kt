package com.example.bigproject

data class ListProperties(
    val title: String,
    val description: String,
    val period: String,
    val color: Int,
    val priority: Priority,
    val type: Type,
    val quantity: String
)

enum class Priority {
    High,
    Medium,
    Low
}

enum class Type {
    Physical,
    Mental
}
