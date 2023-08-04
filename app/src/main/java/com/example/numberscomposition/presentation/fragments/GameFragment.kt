package com.example.numberscomposition.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numberscomposition.R
import com.example.numberscomposition.databinding.FragmentGameBinding
import com.example.numberscomposition.databinding.FragmentWelcomeBinding
import com.example.numberscomposition.domain.models.GameResult
import com.example.numberscomposition.domain.models.GameSettings
import com.example.numberscomposition.domain.models.Level
import java.lang.RuntimeException
import kotlin.random.Random


class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding is null")

    private lateinit var level: Level


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchGameFinishedFragment(GameResult(true,5,5, GameSettings(0,0,0,0)))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        binding.tvSum.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentHolder, GameFinishedFragment.newInstance(gameResult))
                .addToBackStack(null)
                .commit()
        }

    }

    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }

    companion object {
       private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }
    }
}