package com.example.learnenglishwordsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.learnenglishwordsapp.databinding.ActivityLearnWordsBinding
import com.example.learnenglishwordsapp.trainer.LearnWordsTrainer
import trainer.Question

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLearnWordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        with(binding) {
            btnContinue.setOnClickListener {
                layoutResult.isVisible = false
                markAnswerNeutral(layoutAnswer1, tvAnswerNumber1, tvAnswerText1)
                markAnswerNeutral(layoutAnswer2, tvAnswerNumber2, tvAnswerText2)
                markAnswerNeutral(layoutAnswer3, tvAnswerNumber3, tvAnswerText3)
                markAnswerNeutral(layoutAnswer4, tvAnswerNumber4, tvAnswerText4)
                showNextQuestion(trainer)
            }
            btnSkip.setOnClickListener { showNextQuestion(trainer) }
        }

    }

    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val question: Question? = trainer.getNextQuestion()
        with(binding) {

            if (question == null) {
                with(binding) {
                    tvQuestionWord.isVisible = false
                    layoutAnswers.isVisible = false
                    btnSkip.text = "Всё выучено"
                }
            } else {
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true
                tvQuestionWord.text = question.wordToStudy.original

                tvAnswerText1.text = question.answerOptions.elementAt(0).translation
                tvAnswerText2.text = question.answerOptions.elementAt(1).translation
                tvAnswerText3.text = question.answerOptions.elementAt(2).translation
                tvAnswerText4.text = question.answerOptions.elementAt(3).translation

                layoutAnswer1.setOnClickListener {
                    if (trainer.isAnswerCorrect(0)) {
                        markAnswerCorrect(layoutAnswer1, tvAnswerNumber1, tvAnswerText1)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer1, tvAnswerNumber1, tvAnswerText1)
                        showResultMessage(false)
                    }
                }

                layoutAnswer2.setOnClickListener {
                    if (trainer.isAnswerCorrect(1)) {
                        markAnswerCorrect(layoutAnswer2, tvAnswerNumber2, tvAnswerText2)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer2, tvAnswerNumber2, tvAnswerText2)
                        showResultMessage(false)
                    }
                }

                layoutAnswer3.setOnClickListener {
                    if (trainer.isAnswerCorrect(2)) {
                        markAnswerCorrect(layoutAnswer3, tvAnswerNumber3, tvAnswerText3)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer3, tvAnswerNumber3, tvAnswerText3)
                        showResultMessage(false)
                    }
                }

                layoutAnswer4.setOnClickListener {
                    if (trainer.isAnswerCorrect(3)) {
                        markAnswerCorrect(layoutAnswer4, tvAnswerNumber4, tvAnswerText4)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutAnswer4, tvAnswerNumber4, tvAnswerText4)
                        showResultMessage(false)
                    }
                }
            }
        }
    }

    private fun markAnswerNeutral(
        layoutAnswer: LinearLayout,
        tvAnswerNumber: TextView,
        tvAnswerText: TextView,
    ) {
        with(binding) {

            layoutAnswer.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_answer_option
            )

            tvAnswerNumber.apply {
                setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.textAnswerNumberColor
                    ),
                )

                background = ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.shape_rounded_answer_number
                )
            }

            tvAnswerText.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.textAnswerNumberColor
                ),
            )

            layoutResult.isVisible = false
            btnSkip.isVisible = true
        }

    }

    private fun markAnswerCorrect(
        layoutAnswer: LinearLayout,
        tvAnswerNumber: TextView,
        tvAnswerText: TextView,
    ) {
        with(binding) {

            layoutAnswer.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_answer_option_correct
            )

            tvAnswerNumber.apply {
                background = ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.shape_rounded_answer_number_correct
                )

                setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.white
                    )
                )
            }

            tvAnswerText.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.correctAnswer
                )
            )
        }

    }

    private fun markAnswerWrong(
        layoutAnswer: LinearLayout,
        tvAnswerNumber: TextView,
        tvAnswerText: TextView,
    ) {
        with(binding) {

            layoutAnswer.background = ContextCompat.getDrawable(
                this@MainActivity,
                R.drawable.shape_rounded_answer_option_wrong
            )

            tvAnswerNumber.apply {
                background = ContextCompat.getDrawable(
                    this@MainActivity,
                    R.drawable.shape_rounded_answer_number_wrong
                )

                setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.white
                    )
                )
            }

            tvAnswerText.setTextColor(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.wrongAnswer
                )
            )
        }

    }

    private fun showResultMessage(isCorrect: Boolean) {
        val color: Int
        val messageText: String
        val resultIcon: Int

        if (isCorrect) {
            color = ContextCompat.getColor(this, R.color.correctAnswer)
            messageText = "Correct!" //TODO: take text from string resources
            resultIcon = R.drawable.ic_correct

        } else {
            color = ContextCompat.getColor(this, R.color.wrongAnswer)
            messageText = "Wrong" //TODO: take text from string resources
            resultIcon = R.drawable.ic_wrong
        }

        with(binding) {
            layoutResult.setBackgroundColor(color)
            ivResultIcon.setImageResource(resultIcon)
            tvResult.text = messageText
            btnContinue.setTextColor(color)
            btnSkip.isVisible = false
            layoutResult.isVisible = true
        }
    }
}