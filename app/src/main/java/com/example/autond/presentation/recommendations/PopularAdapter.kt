package com.example.autond.presentation.recommendations

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.autond.databinding.ViewholderRecommendedBinding
import com.example.autond.domain.entity.ItemModel
import com.example.autond.domain.entity.OnItemClickListener

class PopularAdapter(
    private val items: MutableList<ItemModel>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding =
            ViewholderRecommendedBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.titleText.text = items[position].title
        holder.binding.priceText.text = items[position].price.toString() + " BYN"

        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            listener.onItemClick(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: ViewholderRecommendedBinding) :
        RecyclerView.ViewHolder(binding.root)
}