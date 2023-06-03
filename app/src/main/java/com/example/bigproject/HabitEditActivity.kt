package com.example.bigproject

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.bigproject.databinding.ActivitySecondBinding

class HabitEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var habitIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(binding.root)

        habitEditChange()

        binding.saveHabit.setOnClickListener {
            saveHabitData()
        }
    }

    private fun saveHabitData() {
        //задаётся здесь, потому что только после кода выше инициализируется объект resources
        val priorityOptions = resources.getStringArray(R.array.priority_options)

        with(binding) {
            // вынести в отдельный метод saveHabit

            val title = editTitle.text.toString()
            val description = editDescription.text.toString()
            val quantity = editQuantity.text.toString()
            val period = editPeriod.text.toString()
            val type = getSelectedType()
            val priority = getChoosenPriority(priorityOptions)
            val color = getColor()

            // проверка на обязательные поля . вынести в отдельный метод validate
            // раздробить код
            if (title.isEmpty() || description.isEmpty() || quantity.isEmpty() || period.isEmpty() || type == null || priority == Priority.Choose || color == 0) {
                Toast.makeText(
                    this@HabitEditActivity,
                    R.string.fill_the_line, // тип Int
                    Toast.LENGTH_SHORT // тип Int
                ).show()
                return
            }
            if (habitIndex != -1) {
                val habit = Habit(title, description, period, color, priority, type, quantity)
                HabitList.updateHabit(habit, habitIndex)
            } else {
                val habit = Habit(title, description, period, color, priority, type, quantity)
                HabitList.addHabit(habit)
            }
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    private fun habitEditChange() {
        habitIndex = intent.getIntExtra("index", -1)

        if (habitIndex != -1) {

            // получает уже заполненную habit
            val habitEdit = HabitList.getHabit(habitIndex)
            fillFieldsWithHabitData(habitEdit)
        }
    }

    // заполняет поля данными, которые были переданы ранее из MainActivity
    private fun fillFieldsWithHabitData(habitEdit: Habit) {
        with(binding) {
            editTitle.setText(habitEdit.title)
            editDescription.setText(habitEdit.description)
            editQuantity.setText(habitEdit.quantity)
            editPeriod.setText(habitEdit.period)

            when (habitEdit.type) {
                Type.Physical -> radioType.check(radioPhysical.id)
                Type.Mental -> radioType.check(radioMental.id)
            }

            when (habitEdit.priority) {
                Priority.Choose -> spinnerPriority.setSelection(0)
                Priority.Low -> spinnerPriority.setSelection(1)
                Priority.Medium -> spinnerPriority.setSelection(2)
                Priority.High -> spinnerPriority.setSelection(3)
            }
            editColor.setText(habitEdit.color.toString())
        }
    }

    // находит отмеченную кнопку и определяет её тип, возвращая полученное значение
    private fun getSelectedType(): Type? {
        with(binding) {
            return when (radioType.checkedRadioButtonId) {
                radioPhysical.id -> Type.Physical
                radioMental.id -> Type.Mental
                else -> null
            }
        }
    }

    //???Почему это работает
    private fun getChoosenPriority(priorityOptions: Array<String>): Priority {
        with(binding) {
            // связываем xml с kotlin
            return when (spinnerPriority.selectedItem.toString()) {
                priorityOptions[0] -> Priority.Choose
                priorityOptions[1] -> Priority.Low
                priorityOptions[2] -> Priority.Medium
                priorityOptions[3] -> Priority.High
                else -> Priority.Choose
            }
        }
    }

    private fun getColor(): Int {
        val colorString = binding.editColor.text.toString()

        return if (colorString.isNotEmpty() && TextUtils.isDigitsOnly(colorString)) {
            colorString.toInt()
        } else 0
    }
}