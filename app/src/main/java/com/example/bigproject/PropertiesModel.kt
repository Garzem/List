package com.example.bigproject

data class Habit(
    val title: String,
    val description: String,
    val period: String,
    //val color: Int,
    val priority: Priority,
    val type: Type,
    val quantity: String
)

enum class Priority {
    High,
    Medium,
    Low,
    Choose
}

enum class Type {
    Physical,
    Mental
}
