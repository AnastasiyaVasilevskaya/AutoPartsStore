package com.example.autond.presentation.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.autond.R
import com.example.autond.databinding.FragmentDashboardBinding
import com.example.autond.domain.entity.ItemModel
import com.example.autond.domain.entity.OnItemClickListener
import com.example.autond.domain.entity.SliderModel
import com.example.autond.presentation.banner.SliderAdapter
import com.example.autond.presentation.category.CategoryAdapter
import com.example.autond.presentation.recommendations.PopularAdapter

class DashboardFragment : Fragment(), OnItemClickListener {

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
        initCategory()
        initRecommendations()
        setOnClickListeners()
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(viewLifecycleOwner) { items ->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        }
        viewModel.loadBanners()
    }

    private fun banners(images: List<SliderModel>) {
        binding.viewpagerSlider.apply {
            adapter = SliderAdapter(images, this)
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.viewpagerSlider.setPageTransformer(compositePageTransformer)
        if (images.size > 1) {
            binding.dotIndicator.apply {
                visibility = View.VISIBLE
                attachTo(binding.viewpagerSlider)
            }

        }
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.categories.observe(viewLifecycleOwner) {
            binding.viewCategory.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility = View.GONE
        }
        viewModel.loadCategories()
    }

    private fun initRecommendations() {
        binding.progressBarPopular.visibility = View.VISIBLE
        viewModel.popular.observe(viewLifecycleOwner) {
            binding.viewPopular.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.viewPopular.adapter = PopularAdapter(it, this)
            binding.progressBarPopular.visibility = View.GONE
        }
        viewModel.loadRecommendations()
    }

    override fun onItemClick(item: ItemModel) {
        val action = DashboardFragmentDirections.actionDashboardFragmentToDetailFragment(item)
        findNavController().navigate(action)
    }

    private fun setOnClickListeners() {
        binding.cartButton.setOnClickListener{
            findNavController().navigate(R.id.action_dashboardFragment_to_cartFragment)
        }
    }
}
