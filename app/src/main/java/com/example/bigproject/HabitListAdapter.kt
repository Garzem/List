package com.example.bigproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bigproject.databinding.ItemLayoutBinding


// объединяет список с Model и указывает, что будет работать со вложенным классом ViewHolder

class HabitListAdapter(
    // принимает аргумент и (unit) действие не возвращает значение
    private val openHabitChange: (index: Int) -> Unit
) : RecyclerView.Adapter<HabitListAdapter.ViewHolder>() {

    // инициализирует пустой список для отображения в RecyclerView

    var habits: MutableList<Habit> = mutableListOf()

    // привязывает макет item_layout к элементам списка

    class ViewHolder(val itemBinding: ItemLayoutBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // создаёт новый view, т.е то, что должно выводиться на экран

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        // описывается получение элемента списка

        val inflater = LayoutInflater.from(parent.context)

        // описывает его создание и перенос в activity_main

        val binding = ItemLayoutBinding.inflate(inflater, parent, false)

        // возвращает уже созданную view

        return ViewHolder(binding)
    }

    // возвращает количество элементов, в данном случае кол-во параметров

    override fun getItemCount(): Int {
        return habits.size
    }

    //привязывает данные

    override fun onBindViewHolder(holder: ViewHolder, index: Int) {
        val currentItem = habits[index]

        // находит каждый элемент списка и устанавливает ему соот. значение из спика habits

        with(holder.itemBinding) {
            title.text = currentItem.title
            description.text = currentItem.description
            period.text = currentItem.period
            quantity.text = currentItem.quantity
            color.setBackgroundColor(currentItem.color)
            priority.text = currentItem.priority.toString()
            type.text = currentItem.type.toString()
        }

        // устанавливает слушатель на элемент списка
        holder.itemView.setOnClickListener {
            openHabitChange(index)
        }
    }

    // принимает список объектов и обновляет их в adapter. благодаря функциям recyclerview
    // идёт обновление одного view, а не всего кода, поэтому не нужно это указывать в коде
    @SuppressLint("NotifyDataSetChanged")
    fun setList(newHabit: List<Habit>) {
        habits = newHabit as MutableList<Habit>
        notifyDataSetChanged()
        // getHabit
    }
}