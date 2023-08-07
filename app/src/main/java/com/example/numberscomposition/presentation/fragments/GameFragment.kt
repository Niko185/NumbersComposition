package com.example.numberscomposition.presentation.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.numberscomposition.R
import com.example.numberscomposition.databinding.FragmentGameBinding
import com.example.numberscomposition.databinding.FragmentWelcomeBinding
import com.example.numberscomposition.domain.models.GameResult
import com.example.numberscomposition.domain.models.GameSettings
import com.example.numberscomposition.domain.models.Level
import java.lang.RuntimeException
import kotlin.random.Random


class GameFragment : Fragment() {

    private lateinit var level: Level

    private val viewModelFactory by lazy {
        GameViewModelFactory(level, requireActivity().application)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    private val textViewVariantsAnswers by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvVariant1)
            add(binding.tvVariant2)
            add(binding.tvVariant3)
            add(binding.tvVariant4)
            add(binding.tvVariant5)
            add(binding.tvVariant6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding is null")


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
        observeObjectsViewModel()
        setClickListenerForVariantAnswers()
    }

    private fun setClickListenerForVariantAnswers(){
        for(variantAnswer in textViewVariantsAnswers){
            variantAnswer.setOnClickListener {
                viewModel.chooseAnswer(variantAnswer.text.toString().toInt())
            }
        }
    }

    private fun observeObjectsViewModel() {
        viewModel.question.observe(viewLifecycleOwner){
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()

            // в цикле в каждый textView установим необходимый вариант ответа
            for(i in 0 until textViewVariantsAnswers.size){
                textViewVariantsAnswers[i].text = it.answersVariants[i].toString()
            }
        }
        // Устанавливаем полученный процент впрогресс бар
        viewModel.percentRightAnswers.observe(viewLifecycleOwner){
            binding.progressBar.setProgress(it, true)
        }
        viewModel.enoughCount.observe(viewLifecycleOwner) {
            val colorVariant = if(it) {
                android.R.color.holo_green_light
            } else {
                android.R.color.holo_red_light
            }
            val color = ContextCompat.getColor(requireContext(), colorVariant)
            binding.tvAnswersProgress.setTextColor(color)
        }
        viewModel.enoughPercent.observe(viewLifecycleOwner){
            val colorVariant = if(it) {
                android.R.color.holo_green_light
            } else {
                android.R.color.holo_red_light
            }
            val color = ContextCompat.getColor(requireContext(), colorVariant)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner){
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner){
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner){
            launchGameFinishedFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner){
            binding.tvAnswersProgress.text = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
    }



    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentHolder, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }



    companion object {
        const val NAME = "game_fragment"
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