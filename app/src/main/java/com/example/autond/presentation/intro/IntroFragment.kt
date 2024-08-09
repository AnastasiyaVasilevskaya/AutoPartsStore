package com.example.autond.presentation.intro

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.autond.R
import com.example.autond.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Анимация для колеса
        introAnimation()
        //Переход по клику
        clickNavigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun introAnimation() {
        binding.wheelIntro.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    binding.wheelIntro.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    // Рассчитываем конечную позицию колеса (край экрана)
                    val screenWidth = binding.root.width
                    val imageViewWidth = binding.wheelIntro.width
                    val startTranslationX = -imageViewWidth.toFloat()
                    val endTranslationX = (screenWidth + imageViewWidth).toFloat()

                    // Анимация перемещения колеса слева направо
                    val wheelAnimator = ObjectAnimator.ofFloat(
                        binding.wheelIntro,
                        "translationX",
                        startTranslationX,
                        endTranslationX
                    ).apply {
                        duration = 4000 // Длительность 4 секунды
                    }

                    // Анимация вращения колеса
                    val rotationAnimator =
                        ObjectAnimator.ofFloat(binding.wheelIntro, "rotation", 0f, 3240f).apply {
                            duration = 4000 // Длительность 4 секунды
                        }

                    // Анимация появления текста
                    val textFadeAnimator =
                        ObjectAnimator.ofFloat(binding.animatedTextIntro, "alpha", 0f, 1f).apply {
                            duration = 2000 // Длительность 2 секунды
                            startDelay = 2000 // Задержка начала 2 секунды
                        }

                    // Запуск всех анимаций вместе
                    AnimatorSet().apply {
                        playTogether(wheelAnimator, rotationAnimator)
                        playSequentially(textFadeAnimator)
                        start()
                    }
                }
            }
        )
    }

    private fun clickNavigation() {
        binding.root.setOnClickListener {
            findNavController().navigate(R.id.action_introFragment_to_loginFragment)
        }
    }
}
