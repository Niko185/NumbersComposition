package com.example.numberscomposition.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.example.numberscomposition.databinding.FragmentGameFinishedBinding
import com.example.numberscomposition.domain.models.GameResult
import java.lang.RuntimeException


class GameFinishedFragment : Fragment() {
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding is null")

    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGameAgain()
            }
        })
        onClickButtonRetryAgain()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onClickButtonRetryAgain(){
        binding.buttonRetry.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack(ChooseLevelFragment.NAME, 0)
        }
    }

    private fun retryGameAgain(){
        requireActivity().supportFragmentManager.popBackStack(ChooseLevelFragment.NAME, 0)
    }

    private fun parseArgs() {
        gameResult = requireArguments().getSerializable(KEY_GAME_RESULT) as GameResult
    }

    companion object {
        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
          return  GameFinishedFragment().apply {
              arguments = Bundle().apply {
                  putSerializable(KEY_GAME_RESULT, gameResult)
              }
          }
        }

    }
}