package com.example.bigproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.example.bigproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // делает более позднюю инициализацию, чтобы не было проблем с отображением активити

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: RecycleViewAdapter
    lateinit var recyclerView: RecyclerView

    // MutableList потому что в дальнейшем должна быть возможность редактировать созданный хобби

    private val itemList: MutableList<ListProperties> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // создаёт связь с макетом activity_main

        binding = ActivityMainBinding.inflate(layoutInflater)

        // определяет какой макет будет отображаться при открытии активити
        // в данном случае это корневой view, т.е activity_main

        setContentView(binding.root)

        // ???разобрать этот блок

        // задаёт переменную recyclerView, которая ссылается на RecycleViewHabit

        recyclerView = binding.RecycleViewHabit

        // в переменную adapter передаётся список элементов из RecycleViewAdapter

        adapter = RecycleViewAdapter(itemList)

        // устанавливает adapter для RecyclerView

        recyclerView.adapter = adapter

        // обработчик нажатий FAB

        binding.button.setOnClickListener {
            launchSecondActivity()
        }
    }

    // создаётся получатель данных из второй активити, который сверяет информацию (if).
    // в result определяет код для обработки результата из второй активити

    @SuppressLint("NotifyDataSetChanged")
    private val secondActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val title = data?.getStringExtra("title") ?: ""
            val description = data?.getStringExtra("description") ?: ""
            val quantity = data?.getStringExtra("quantity") ?: ""
            val period = data?.getStringExtra("period") ?: ""
            val priorityGet = data?.getStringExtra("priority")
            val typeGet = data?.getStringExtra("type")
            val colorGet = data?.getStringExtra("color")

            // сравнивает равно ли значение из enum class полученному, если да,
            // то показывает его, если нет, то по умолчанию

            val priority = Priority.values().find { it.name == priorityGet } ?: Priority.Low
            val type = Type.values().find { it.name == typeGet } ?: Type.Physical

            // преобразует String в Int или null

            val color = colorGet?.toIntOrNull() ?:0

            val item = ListProperties(title, description, period, color, priority, type, quantity)
            itemList.add(item)
            adapter.notifyDataSetChanged()
        }
    }

    // этот метод используется для запуска SecondActivity с помощью ActivityResultLauncher
    // , который был зарегистрирован ранее

    private fun launchSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        secondActivityLauncher.launch(intent)
    }

    // обработчик нажатий на элемент списка

    fun onItemClick(item: ListProperties) {

    }
}
