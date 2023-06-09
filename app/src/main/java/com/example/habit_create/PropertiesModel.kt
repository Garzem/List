package com.example.habit_create

data class Habit(
    val title: String,
    val description: String,
    val period: String,
    val color: Int,
    val priority: Priority,
    val type: Type,
    val quantity: String
)

enum class Priority {
    HIGH,
    MEDIUM,
    LOW,
    CHOOSE
}

enum class Type {
    PHYSICAL,
    MENTAL
}
