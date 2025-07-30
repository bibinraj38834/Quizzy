package com.project.quizzy.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.project.quizzy.R
import com.project.quizzy.databinding.FragmentSplashBinding
import com.project.quizzy.viewmodel.QuizUiState
import com.project.quizzy.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadQuestions()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is QuizUiState.Loading -> {
                            binding.lottieView.visibility = View.VISIBLE
                            binding.errorText.visibility = View.GONE
                            binding.retryButton.visibility = View.GONE
                        }
                        is QuizUiState.Success -> {
                            binding.lottieView.visibility = View.GONE
                            findNavController().navigate(R.id.action_splash_to_quiz)
                        }
                        is QuizUiState.Error -> {
                            binding.lottieView.visibility = View.GONE
                            binding.errorText.visibility = View.VISIBLE
                            binding.errorText.text = state.message
                            binding.retryButton.visibility = View.VISIBLE
                            binding.retryButton.setOnClickListener {
                                viewModel.loadQuestions()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
