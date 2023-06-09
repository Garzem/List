package com.example.habit_create

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bigproject.R
import com.example.bigproject.databinding.ActivitySecondBinding


class HabitEditActivity : AppCompatActivity(), ColorChooseDialog.OnInputListener {

    private var binding: ActivitySecondBinding? = null
    private var habitIndex: Int = -1
    private var selectedColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(binding!!.root)

        val chooseColorButton = binding!!.chooseColorButton
        chooseColorButton.setOnClickListener {
            colorDialog()
        }

        habitEditChange()

        binding!!.saveHabit.setOnClickListener {
            saveHabitData()
        }
    }

    private fun saveHabitData() {
        //задаётся здесь, потому что только после кода выше инициализируется объект resources
        val priorityOptions = resources.getStringArray(R.array.priority_options)
        binding?.let {
            with(it) {
                val title = editTitle.text.toString()
                val description = editDescription.text.toString()
                val quantity = editQuantity.text.toString()
                val period = editPeriod.text.toString()
                val type = getSelectedType()
                val priority = getChosenPriority(priorityOptions)
                val color = selectedColor

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
        with(binding!!) {
            editTitle.setText(habitEdit.title)
            editDescription.setText(habitEdit.description)
            editQuantity.setText(habitEdit.quantity)
            editPeriod.setText(habitEdit.period)

            when (habitEdit.type) {
                Type.PHYSICAL -> radioType.check(radioPhysical.id)
                Type.MENTAL -> radioType.check(radioMental.id)
            }

            when (habitEdit.priority) {
                Priority.CHOOSE -> spinnerPriority.setSelection(0)
                Priority.LOW -> spinnerPriority.setSelection(1)
                Priority.MEDIUM -> spinnerPriority.setSelection(2)
                Priority.HIGH -> spinnerPriority.setSelection(3)
            }
            chooseColorButton.setBackgroundColor(habitEdit.color)
        }
    }

    // находит отмеченную кнопку и определяет её тип, возвращая полученное значение
    private fun getSelectedType(): Type? {
        with(binding!!) {
            return when (radioType.checkedRadioButtonId) {
                radioPhysical.id -> Type.PHYSICAL
                radioMental.id -> Type.MENTAL
                else -> null
            }
        }
    }


    //???Почему это работает
    private fun getChosenPriority(priorityOptions: Array<String>): Priority {
        binding?.let {
                // связываем xml с kotlin
                return when (it.spinnerPriority.selectedItem.toString()) {
                    priorityOptions[0] -> Priority.CHOOSE
                    priorityOptions[1] -> Priority.LOW
                    priorityOptions[2] -> Priority.MEDIUM
                    priorityOptions[3] -> Priority.HIGH
                    else -> Priority.CHOOSE
            }
        }
        return Priority.CHOOSE
    }

    //открывает ColorChooseDialog
    private fun colorDialog() {
        val colorChooseDialog = ColorChooseDialog()
        colorChooseDialog.show(supportFragmentManager, "color_dialog")
    }

    //???зачем мне это обрабатывать здесь? Почему я не могу это сделать в диалоге? (написал почти сам)
    override fun sendColor(colorChoose: Int) {
        selectedColor = colorChoose
        binding?.chooseColorButton?.setBackgroundColor(colorChoose)
    }
}