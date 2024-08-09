package com.example.autond.presentation.cart

import android.content.Context
import android.icu.text.Transliterator.Position
import android.widget.Toast
import com.example.autond.domain.entity.ItemModel

class ManagementCart(val context: Context) {

    private val tinyDB = TinyDB(context)

    fun insert(item: ItemModel) {
        val list = getListCart()
        val existAlready = list.any {it.title == item.title}
        val index = list.indexOfFirst {it.title == item.title}

        if(existAlready) {
            list[index].numberInCart = item.numberInCart
        } else {
            list.add(item)
        }
        tinyDB.putListObject("CartList", list, ItemModel::class.java)
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<ItemModel> {
        return tinyDB.getListObject("CartList") ?: arrayListOf()
    }

    fun plusItem(list: ArrayList<ItemModel>, position: Int, listener: ChangeNumberItemsListener) {
        list[position].numberInCart++
        tinyDB.putListObject("CartList", list, ItemModel::class.java)
        listener.onChanged()
    }

    fun minusItem(list: ArrayList<ItemModel>, position: Int, listener: ChangeNumberItemsListener) {
        if (list[position].numberInCart == 1) {
            list.removeAt(position)
        } else {
            list[position].numberInCart--
        }
        tinyDB.putListObject("CartList", list, ItemModel::class.java)
        listener.onChanged()
    }

    fun getTotalFee(): Double {
        val list = getListCart()
        var totalFee = 0.0
        for (item in list) {
            totalFee += item.price * item.numberInCart
        }
        return totalFee
    }
}