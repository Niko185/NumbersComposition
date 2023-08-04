package com.example.numberscomposition.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.numberscomposition.R
import com.example.numberscomposition.databinding.FragmentChooseLevelBinding
import com.example.numberscomposition.domain.models.Level
import java.lang.RuntimeException


class ChooseLevelFragment : Fragment() {
    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchGameFragmentTest(Level.TEST)
        launchGameFragmentEasy(Level.EASY)
        launchGameFragmentNormal(Level.NORMAL)
        launchGameFragmentHard(Level.HARD)
    }

    private fun launchGameFragmentTest(level: Level) {
        binding.buttonLevelTest.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentHolder, GameFragment.newInstance(level))
                .addToBackStack(null)
                .commit()
        }
    }
    private fun launchGameFragmentEasy(level: Level) {
        binding.buttonLevelEasy.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentHolder, GameFragment.newInstance(level))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun launchGameFragmentNormal(level: Level) {
        binding.buttonLevelNoramal.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentHolder, GameFragment.newInstance(level))
                .addToBackStack(null)
                .commit()
        }
    }

    private fun launchGameFragmentHard(level: Level) {
        binding.buttonLevelHard.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentHolder, GameFragment.newInstance(level))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val NAME = "choose_level_fragment"

        fun newInstance() = ChooseLevelFragment()
    }
}