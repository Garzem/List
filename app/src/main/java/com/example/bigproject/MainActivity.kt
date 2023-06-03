package com.example.bigproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bigproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // делает более позднюю инициализацию, чтобы не было проблем с отображением активити

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: HabitListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // создаёт связь с макетом activity_main

        binding = ActivityMainBinding.inflate(layoutInflater)

        // определяет какой макет будет отображаться при открытии активити
        // в данном случае это корневой view, т.е activity_main

        setContentView(binding.root)

        // задаёт переменную recyclerView, которая ссылается на RecycleViewHabit

        recyclerView = binding.RecycleViewHabit

        // в переменную adapter передаётся список элементов из RecycleViewAdapter

        adapter = HabitListAdapter(::openHabitChange)

        // устанавливает adapter для RecyclerView, обращаясь к нему и
        // переопределяя переменную adapter, которая изначально задана в RecyclerView

        recyclerView.adapter = adapter

        val swipeToDeleteCallback = object : SwipeToDeleteCallback(){
            //в аргументе указывается viewHolder, который смахивается и direction - направление
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                adapter.habits.removeAt(index)
                recyclerView.adapter?.notifyItemRemoved(index)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)

        itemTouchHelper.attachToRecyclerView(recyclerView)
        // обработчик нажатий FAB

        binding.button.setOnClickListener {
            launchSecondActivity()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private val secondActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            adapter.setList(HabitList.getHabits())

            // работает на все строки

            adapter.notifyDataSetChanged()
        }
    }

    // этот метод используется для запуска SecondActivity с помощью ActivityResultLauncher
    // , который был зарегистрирован ранее

    private fun launchSecondActivity() {
        val intent = Intent(this, HabitEditActivity::class.java)
        secondActivityLauncher.launch(intent)
    }


    private fun openHabitChange(index: Int) {
        val intent = Intent(this, HabitEditActivity::class.java).apply {
            putExtra("index", index)
        }
        secondActivityLauncher.launch(intent)
    }
}