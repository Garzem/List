package com.example.habit_create

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bigproject.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    // делает более позднюю инициализацию, чтобы не было проблем с отображением активити
    private var binding: ActivityMainBinding? = null
    private lateinit var adapter: HabitListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // создаёт связь с макетом activity_main
        binding = ActivityMainBinding.inflate(layoutInflater)

        // определяет какой макет будет отображаться при открытии активити
        // в данном случае это корневой view, т.е activity_main
        setContentView(binding!!.root)

        // задаёт переменную recyclerView, которая ссылается на RecycleViewHabit
        recyclerView = binding!!.recycleViewHabit

        // в переменную adapter передаётся список элементов из RecycleViewAdapter
        adapter = HabitListAdapter(::openHabitChange)

        // устанавливает adapter для RecyclerView, обращаясь к нему и
        // переопределяя переменную adapter, которая изначально задана в RecyclerView
        recyclerView.adapter = adapter

        // обработчик нажатий FAB
        binding!!.buttonFab.setOnClickListener {
            launchSecondActivity()
        }

        swipeDelete()
    }

    @SuppressLint("NotifyDataSetChanged")
    private val secondActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                adapter.setList(HabitList.getHabits())
                // работает на все строки
                adapter.notifyDataSetChanged()
                //сверяет layoutParams кнопки с тем, чтобы они были типа CoordinatorLayout
                val layoutFabParams = binding?.buttonFab?.layoutParams as CoordinatorLayout.LayoutParams
                layoutFabParams.let {
                    it.gravity = Gravity.BOTTOM or Gravity.END
                    //меняет расположение кнопки
                    binding?.buttonFab?.layoutParams = it
                }
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

    private fun swipeDelete() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            //в аргументе указывается viewHolder, который смахивается и direction - направление
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                HabitList.deleteHabit(index)
                adapter.notifyItemRemoved(index)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}