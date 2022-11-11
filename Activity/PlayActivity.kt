package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.myapplication.databinding.ActivityPlayBinding
import kotlin.random.Random

class PlayActivity : AppCompatActivity() {
    lateinit var bindingClass : ActivityPlayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        // Подготовка

        val num_player = intent.getIntExtra("answer", 45)
        var min_value = intent.getIntExtra("min", 0)
        var max_value = intent.getIntExtra("max", 100)
        var avg = (max_value + min_value) / 2

       if (max_value - min_value % 2 != 0)
            max_value += 1

        var end_game = false
        var player_is_win =  false

        // Обработка игры

        val temp = resources.getString(R.string.play_question_text) + "[$min_value, $avg]?"
        bindingClass.headTextQuest.text = temp

        bindingClass.buttonYes.setOnClickListener(View.OnClickListener {
            if (!end_game)
            {
                if (avg - min_value <= 2)
                {
                    end_game = true
                    bindingClass.buttonYes.visibility = View.INVISIBLE
                    bindingClass.buttonNo.visibility = View.INVISIBLE
                    val num = Random.nextInt(min_value, avg + 1)

                    if (num == num_player)
                    {
                        val temp = resources.getString(R.string.play_fail_text) + "$num"
                        bindingClass.headTextQuest.text = temp
                    }
                    else
                    {
                        val temp = resources.getString(R.string.play_win_text) + "$num"
                        bindingClass.headTextQuest.text = temp
                        player_is_win = true
                    }
                }
                else
                {
                    max_value = avg
                    avg = (max_value + min_value) / 2
                    val temp = resources.getString(R.string.play_question_text) + "[$min_value, $avg]?"
                    bindingClass.headTextQuest.text = temp
                }
            }
        })

        bindingClass.buttonNo.setOnClickListener(View.OnClickListener {
            if (!end_game)
            {
                if (max_value - avg <= 2)
                {
                    end_game = true
                    bindingClass.buttonYes.visibility = View.INVISIBLE
                    bindingClass.buttonNo.visibility = View.INVISIBLE
                    val num = Random.nextInt(avg + 1, max_value + 1)

                    if (num == num_player)
                    {
                        val temp = resources.getString(R.string.play_fail_text) + "$num"
                        bindingClass.headTextQuest.text = temp
                    }
                    else
                    {
                        val temp = resources.getString(R.string.play_win_text) + "$num"
                        bindingClass.headTextQuest.text = temp
                        player_is_win = true
                    }
                }
                else
                {
                    min_value = avg
                    avg = (max_value + min_value) / 2
                    val temp = resources.getString(R.string.play_question_text) + "[$min_value, $avg]?"
                    bindingClass.headTextQuest.text = temp
                }
            }
        })

        bindingClass.buttonBack.setOnClickListener(View.OnClickListener {
            if (end_game)
            {
                intent.putExtra("res", player_is_win)
                setResult(RESULT_OK, intent)
            }

            finish()
        })
    }
}