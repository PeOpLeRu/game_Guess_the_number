package com.example.myapplication

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.graphics.toColor
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var bindingClass : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        bindingClass.mainButton.setOnClickListener(View.OnClickListener { onClickButton() })
    }

    private fun onClickButton() {
        if (bindingClass.minValue.text.toString() == "" || bindingClass.maxValue.text.toString() == "" || bindingClass.answerValue.text.toString() == "")
        {
            bindingClass.mainLastResText.text = resources.getString(R.string.main_error_input_info_text)
            bindingClass.mainLastResText.setTextColor(resources.getColor(R.color.red))
            return
        }

        if (bindingClass.maxValue.text.toString().toInt() < bindingClass.answerValue.text.toString().toInt() ||
            bindingClass.minValue.text.toString().toInt() > bindingClass.answerValue.text.toString().toInt())
        {
            bindingClass.mainLastResText.text = resources.getString(R.string.main_error_input_answer_info_text)
            bindingClass.mainLastResText.setTextColor(resources.getColor(R.color.red))
            return
        }

        val intent = Intent(this, PlayActivity::class.java)

        intent.putExtra("min", bindingClass.minValue.text.toString().toInt())
        intent.putExtra("max", bindingClass.maxValue.text.toString().toInt())
        intent.putExtra("answer", bindingClass.answerValue.text.toString().toInt())

        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null)
        {
            if (requestCode == 0)
            {
                if (data.getBooleanExtra("res", false))
                {
                    bindingClass.mainLastResText.text = resources.getString(R.string.main_win_info_text)
                    bindingClass.mainLastResText.setTextColor(resources.getColor(R.color.green))

                }
                else
                {
                    bindingClass.mainLastResText.text = resources.getString(R.string.main_fail_info_text)
                    bindingClass.mainLastResText.setTextColor(resources.getColor(R.color.red))
                }
            }
        }
    }
}