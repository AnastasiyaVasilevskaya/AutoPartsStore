package com.example.autond.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.autond.R
import com.example.autond.databinding.FragmentDetailBinding
import com.example.autond.domain.entity.ItemModel
import com.example.autond.domain.entity.SliderModel
import com.example.autond.presentation.banner.SliderAdapter
import com.example.autond.presentation.cart.ManagementCart

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var item: ItemModel
    private var numberOder = 1
    private lateinit var managementCart: ManagementCart

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        managementCart = ManagementCart(requireContext())

        getBundle()
        banners()
    }

    private fun getBundle() {
        item = args.item
        binding.titleText.text = item.title
        binding.descriptionText.text = item.description
        binding.priceText.text = item.price.toString() + "BYN"
        binding.addToCartButton.setOnClickListener {
            item.numberInCart = numberOder
            managementCart.insert(item)
        }
        binding.backButton.setOnClickListener{
            findNavController().navigateUp()
        }

        binding.cartButton.setOnClickListener{
            findNavController().navigate(R.id.action_detailFragment_to_cartFragment)
        }

    }

    private fun banners() {
        val sliderItems = ArrayList<SliderModel>()
        for (imageUrl in item.picUrl) {
            sliderItems.add(SliderModel(imageUrl))
        }

        binding.slider.apply {
            adapter = SliderAdapter(sliderItems, binding.slider)
            clipToPadding = true
            clipChildren = true
            offscreenPageLimit = 1
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            if (sliderItems.size > 1) {
                binding.dotIndicator.visibility = View.VISIBLE
                binding.dotIndicator.attachTo(binding.slider)
            }
        }
    }
}