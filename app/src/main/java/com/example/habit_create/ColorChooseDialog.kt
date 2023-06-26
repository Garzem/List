package com.example.habit_create

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.bigproject.R
import com.example.bigproject.databinding.HorizontalColorChooseBinding


class ColorChooseDialog: DialogFragment() {

    private var buttons: ArrayList<View>? = null
    private var binding: HorizontalColorChooseBinding? = null
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
        buttons = binding!!.linearColorButtons.touchables
        buttons!!.forEach { button ->
            button.setOnClickListener {
                //it указывается для того, чтобы не учитывать нажатий по другим элементам, кроме button, если они есть
                //проверяем является ли drawable экземпляром GradientDrawable
                onInputListener!!.sendColor((it as Button).currentHintTextColor)
                dismiss()
            }
        }
    }

    interface OnInputListener {
        fun sendColor(colorChoose: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onInputListener = requireActivity() as OnInputListener
    }
}