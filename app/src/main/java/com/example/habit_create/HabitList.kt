package com.example.habit_create

object HabitList {

    private val habitsList = mutableListOf<Habit>()

    fun addHabit(habit: Habit) = habitsList.add(habit)

    // set заменяет значение habit на указанном индексе

    fun updateHabit(habit: Habit, index: Int) = habitsList.set(index, habit)
    //??
    fun deleteHabit(index: Int) {
        habitsList.apply { drop(index) }
    }

    // toList нужен для того, чтобы список не менялся, кроме случаев со специальным методом
    fun getHabits() = habitsList.toList()

    fun getHabit(index: Int) = habitsList[index]
}