package com.example.autond.presentation.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.autond.R
import com.example.autond.databinding.FragmentCartBinding


class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private lateinit var managementCart: ManagementCart
    private var tax: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        managementCart = ManagementCart(requireContext())

        setVariable()
        initCartList()
    }

    private fun setVariable() {
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initCartList() {
        binding.viewCart.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.viewCart.adapter = CartAdapter(
            managementCart.getListCart(),
            requireContext(),
            object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()
                }
            })

        with(binding) {
            emptyCart.visibility =
                if (managementCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
            scrollView.visibility =
                if (managementCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun calculateCart() {
        val delivery = 10.0
        val total = Math.round((managementCart.getTotalFee() + delivery) * 100) / 100
        val itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100

        with(binding) {
            subtotal.text = "$itemTotal BYN"
            deliveryText.text = "$delivery BYN"
            totalsum.text = "$total BYN"
        }
    }
}