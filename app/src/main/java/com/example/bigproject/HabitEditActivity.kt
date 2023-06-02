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
        with(binding) {
            // вынести в отдельный метод saveHabit

            val title = editTitle.text.toString()
            val description = editDescription.text.toString()
            val quantity = editQuantity.text.toString()
            val period = editPeriod.text.toString()

            // находит отмеченную кнопку и определяет её тип

            val type = when (radioType.checkedRadioButtonId) {
                radioPhysical.id -> Type.Physical
                radioMental.id -> Type.Mental
                else -> null
            }

            // находит выбранное значение и переводит в текст

            val spinner = spinnerPriority.selectedItem.toString()
            val colorString = editColor.text.toString()

            val color =
                if (colorString.isNotEmpty() && TextUtils.isDigitsOnly(colorString)) {
                    colorString.toInt()
                } else {
                    0
                }

            // связываем xml с kotlin

            val priority = when (spinner) {
                "Низкий" -> Priority.Low
                "Средний" -> Priority.Medium
                "Высокий" -> Priority.High
                else -> Priority.Choose
            }

            // проверка на обязательные поля . вынести в отдельный метод validate

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
        // получить индекс и по этому индексу с помощью getHabit получить привычку и заполнить все поля для редактирования
    }

    private fun habitEditChange() {
        val index = intent.getIntExtra("index", -1)

        if (index != -1) {

            // получает уже заполненную habit
            val habitEdit = HabitList.getHabit(index)
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
                Priority.Low -> spinnerPriority.setSelection(1)
                Priority.Medium -> spinnerPriority.setSelection(2)
                Priority.High -> spinnerPriority.setSelection(3)
                else -> Priority.Choose // почему здесь надо else, а в коде выше можно без него?
            }
            editColor.setText(habitEdit.color.toString())
        }
    }
}

//    private fun saveHabitChange() {
//        HabitList.updateHabit()
//    }
//}
