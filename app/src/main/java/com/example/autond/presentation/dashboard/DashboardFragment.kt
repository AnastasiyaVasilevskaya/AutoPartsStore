package com.example.autond.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.autond.databinding.FragmentDashboardBinding
import com.example.autond.domain.entity.SliderModel

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    private val viewModel = DashboardViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBanner()
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(viewLifecycleOwner) { items ->

        }
    }

    private fun banners(images: List<SliderModel>) {
        binding.viewpagerSlider.adapter = SliderAdapter(images, binding.viewpagerSlider)
        binding.viewpagerSlider.clipToPadding = false
    }

}
