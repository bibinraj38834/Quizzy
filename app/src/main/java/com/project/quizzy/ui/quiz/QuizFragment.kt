package com.project.quizzy.ui.quiz

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.project.quizzy.R
import com.project.quizzy.viewmodel.QuizUiState
import kotlinx.coroutines.launch
import com.project.quizzy.databinding.FragmentQuizBinding
import com.project.quizzy.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var lastQuestionIndex: Int = -1

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        resetProgress()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state is QuizUiState.Success) {
                        val q = state.questions.getOrNull(state.currentIndex)

                        if (q == null) {
                            findNavController().navigate(R.id.action_quiz_to_result)
                        } else {
                            // question
                            binding.questionText.text = q.question

                            // options
                            binding.option1Text.text = q.options[0]
                            binding.option2Text.text = q.options[1]
                            binding.option3Text.text = q.options[2]
                            binding.option4Text.text = q.options[3]

                            if (state.currentIndex != lastQuestionIndex) {
                                updateProgressSmooth(state.currentIndex, state.questions.size)
                                lastQuestionIndex = state.currentIndex
                            }

                            // streak + fire
                            binding.streakBadge.text = "${state.streak} IN A ROW"
                            binding.streakBadge.visibility = View.GONE
                            if (state.streak >= 3) {
                                binding.streakFire.visibility = View.VISIBLE
                                binding.streakBadge.visibility = View.VISIBLE
                                binding.streakFire.playAnimation()
                            } else {
                                binding.streakFire.visibility = View.GONE
                                binding.streakBadge.visibility = View.GONE
                                binding.streakFire.cancelAnimation()
                            }

                            // question counter
                            binding.questionCounter.text = "${state.currentIndex + 1}/${state.questions.size}"
                        }
                    }
                }
            }
        }

        val optionCards = listOf(
            binding.option1Card to binding.option1Text,
            binding.option2Card to binding.option2Text,
            binding.option3Card to binding.option3Text,
            binding.option4Card to binding.option4Text
        )

        optionCards.forEachIndexed { idx, (card, _) ->
            card.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.press_down))
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> v.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.release_up))
                }
                false
            }

            card.setOnClickListener {
                handleAnswerClick(idx, optionCards)
            }
        }

        binding.skipButton.setOnClickListener {
            viewModel.skip()
            viewModel.next()
        }
    }

    private fun handleAnswerClick(selectedIndex: Int, optionCards: List<Pair<CardView, TextView>>) {
        val state = viewModel.uiState.value
        if (state is QuizUiState.Success) {
            val q = state.questions[state.currentIndex]

            optionCards.forEachIndexed { idx, (card, text) ->
                when {
                    idx == q.correctOptionIndex -> {
                        card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.correct_green))
                        card.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.pop_scale))
                        text.setTextColor(getResources().getColor(R.color.white))
                    }
                    idx == selectedIndex && idx != q.correctOptionIndex -> {
                        card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.wrong_red))
                        card.startAnimation(AnimationUtils.loadAnimation(requireContext(), R.anim.shake))
                        text.setTextColor(getResources().getColor(R.color.white))
                    }
                    else -> {
                        card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }
                }
                card.isClickable = false
            }

            viewModel.answer(selectedIndex)

            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.next()
                resetOptionStyles()
            }, 2000)
        }
    }

    private fun resetOptionStyles() {
        listOf(binding.option1Card, binding.option2Card, binding.option3Card, binding.option4Card).forEach {
            it.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
            it.isClickable = true
        }
        listOf(binding.option1Text,binding.option2Text,binding.option3Text,binding.option4Text).forEach {
            it.setTextColor(getResources().getColor(R.color.black))
        }
    }

    private fun updateProgressSmooth(currentIndex: Int, total: Int) {
        val prevFraction = (currentIndex - 1).toFloat() / total.toFloat()
        val nextFraction = (currentIndex).toFloat() / total.toFloat()

        binding.lottieProgress.setMinAndMaxProgress(prevFraction, nextFraction)
        binding.lottieProgress.playAnimation()
    }

    private fun resetProgress() {
        binding.lottieProgress.cancelAnimation()
        binding.lottieProgress.progress = 0f
        lastQuestionIndex = -1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
