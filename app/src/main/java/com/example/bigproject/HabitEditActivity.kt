package com.example.bigproject

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.bigproject.databinding.ActivitySecondBinding

class HabitEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // расписано, что происходит при нажатии на кнопку сохранения
        with(binding) {
            saveHabit.setOnClickListener {

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

                val color = if (colorString.isNotEmpty() && TextUtils.isDigitsOnly(colorString)) {
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
                    return@setOnClickListener
                }

                val habit = Habit(title, description, period, color, priority, type, quantity)
                HabitList.addHabit(habit)
                setResult(Activity.RESULT_OK)
                finish()
            }
            // получить индекс и по этому индексу с помощью getHabit получить привычку и заполнить все поля для редактирования
        }

    }

    private fun saveHabitChange() {

    }
}
