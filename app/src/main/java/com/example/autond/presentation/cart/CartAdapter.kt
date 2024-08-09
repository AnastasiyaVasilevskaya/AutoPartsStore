package com.example.autond.presentation.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.autond.databinding.FragmentDashboardBinding
import com.example.autond.databinding.ViewholderCartBinding
import com.example.autond.domain.entity.ItemModel

class CartAdapter(
    private val listItemSelected: ArrayList<ItemModel>,
    context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener? = null
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(val binding: ViewholderCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    private val managementCart = ManagementCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItemSelected[position]

        holder.binding.title.text = item.title
        holder.binding.feeEachItem.text = "BYN${item.price}"
        holder.binding.totalEachItem.text = "BYN${Math.round(item.numberInCart * item.price)}"
        holder.binding.numberItemText.text = item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .apply(RequestOptions().transform(CenterCrop()))
            .into(holder.binding.pic)

        holder.binding.plusCartButton.setOnClickListener {
            managementCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }

        holder.binding.minusCartButton.setOnClickListener {
            managementCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }
    }
    override fun getItemCount(): Int = listItemSelected.size
}