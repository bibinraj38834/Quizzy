package com.project.quizzy.ui.result

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.project.quizzy.R
import com.project.quizzy.databinding.FragmentResultBinding
import com.project.quizzy.viewmodel.QuizUiState
import com.project.quizzy.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val state = viewModel.uiState.value
        if (state is QuizUiState.Success) {
            animateScore(state.score, state.questions.size)
            binding.skippedText.text = "Skipped: ${state.skipped}"
            binding.streakText.text = "Longest Streak: ${state.longestStreak}"
        }

        // Restart
        binding.restartButton.setOnClickListener {
            viewModel.loadQuestions() // reset quiz state
            findNavController().navigate(R.id.splashFragment)
        }

        // Exit
        binding.exitButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    private fun animateScore(finalScore: Int, total: Int) {
        val animator = ValueAnimator.ofInt(0, finalScore)
        animator.duration = 1500 // 1.5s
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            binding.scoreText.text = "Score: $value/$total"
        }
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
