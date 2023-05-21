package com.example.bigproject

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bigproject.databinding.ItemLayoutBinding


// объединяет список с Model и указывает, что будет работать со вложенным классом ViewHolder

class RecycleViewAdapter(
    private val onItemClick: MutableList<ListProperties>
) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    // инициализирует пустой список для отображения в RecyclerView

    private var habits = emptyList<ListProperties>()

    // привязывает макет item_layout к элементам списка

    class ViewHolder(val itemBinding: ItemLayoutBinding)  : RecyclerView.ViewHolder(itemBinding.root)

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = habits[position]

        // находит каждый элемент списка и устанавливает ему соот. значение из спика habits

        holder.itemBinding.title.text = currentItem.title
        holder.itemBinding.description.text = currentItem.description
        holder.itemBinding.period.text = currentItem.period
        holder.itemBinding.quantity.text = currentItem.quantity
        holder.itemBinding.color.setBackgroundColor(currentItem.color)
        holder.itemBinding.priority.text = currentItem.priority.toString()
        holder.itemBinding.type.text = currentItem.type.toString()

    }

    // принимает список объектов и обновляет их в adapter

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newHabit: List<ListProperties>) {
        habits = newHabit
        notifyDataSetChanged()
    }

}