package com.example.bigproject

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.bigproject.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // связываем kotlin и xml

        val editTitle = binding.editTitle
        val editDescription = binding.editDescription
        val spinnerPriority = binding.spinnerPriority
        val radioType = binding.radioType
        val editQuantity = binding.editQuantity
        val editPeriod = binding.editPeriod
        val saveHabit = binding.saveHabit
        val editColor = binding.editColor

        val radioButtonPhysical = binding.radioPhysical
        val radioButtonMental = binding.radioMental

        // расписано, что происходит при нажатии на кнопку сохранения

        saveHabit.setOnClickListener {
            val title = editTitle.text.toString()
            val description = editDescription.text.toString()
            val quantity = editQuantity.text.toString()
            val period = editPeriod.text.toString()

            // находит отмеченную кнопку

            val type = when (radioType.checkedRadioButtonId) {
                radioButtonPhysical.id -> Type.Physical
                radioButtonMental.id -> Type.Mental
                else -> -1
            }

            // находит выбранное значение и переводит в текст

            val spinner = spinnerPriority.selectedItem.toString()
            val color = editColor.text.toString()

            // проверка на обязательные поля

            if (title.isEmpty() || description.isEmpty() || quantity.isEmpty() || period.isEmpty() || type == -1 || spinner == "Выбирете значение" || color.isEmpty()) {
                Toast.makeText(
                    this,
                    "Пожалуйста, заполните все поля и выбирете значения",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            // связываем xml с kotlin

            val priority = when (spinner) {
                "Низкий" -> Priority.Low
                "Средний" -> Priority.Medium
                "Высокий" -> Priority.High
                else -> Priority.Low
            }

            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("title", title)
                putExtra("description", description)
                putExtra("quantity", quantity)
                putExtra("period", period)
                putExtra("type", type)
                putExtra("priority", priority)
                putExtra("color", color)
            }

            // устанавливает, что результат выполнения текущей активити успешен и передаёт данные обратно

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }
}