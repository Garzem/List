package com.example.habit_create

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.bigproject.R
import com.example.bigproject.databinding.HorizontalColorChooseBinding


class ColorChooseDialog: DialogFragment() {

    private lateinit var buttons: ArrayList<View>
    private lateinit var binding: HorizontalColorChooseBinding
    private var onInputListener: OnInputListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //???возвращает раздутый xml, который помещается в родительскую
        //???ViewGroup, но не будет автоматически к нему добавлен
        return inflater.inflate(R.layout.horizontal_color_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bind принимает представление view в качестве параметра
        //и создает экземпляр привязки (binding) для этого представления
        binding = HorizontalColorChooseBinding.bind(view)
        //получает все элементы доступные для нажатия
        buttons = binding.linearColorButtons.touchables
        buttons.forEach {button ->
            button.setOnClickListener{
                //it указывается для того, чтобы не учитывать нажатий по другим элементам, кроме button, если они есть
                val drawable = (it as Button).background
                //проверяем является ли drawable экземпляром GradientDrawable
                if (drawable is GradientDrawable) {
                    //достаём цвет и т.к нет смены цвета при нажатии, то берём дефолтный цвет
                    val colorStateList = drawable.color
                    var color = colorStateList?.defaultColor
                    //задаём исключение
                    if (color != null) {
                        onInputListener!!.sendColor(color)
                    } else {
                        //получает сразу числовой цвет
                        color = ContextCompat.getColor(requireContext(), R.color.white)
                        onInputListener!!.sendColor(color)
                    }
                    dismiss()
                }
            }
        }
    }

    interface OnInputListener {
        fun sendColor(color: Int)
    }
}