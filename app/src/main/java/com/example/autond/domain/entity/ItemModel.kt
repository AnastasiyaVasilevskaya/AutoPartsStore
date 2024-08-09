package com.example.autond.domain.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//@Parcelize
data class ItemModel(
    var title: String = "",
    var description: String = "",
    var picUrl: ArrayList<String> = ArrayList(),
    var price: Double = 0.0,
    var numberInCart: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.createStringArrayList() as ArrayList<String>,
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(description)
        dest.writeStringList(picUrl)
        dest.writeDouble(price)
        dest.writeInt(numberInCart)
    }

    companion object CREATOR : Parcelable.Creator<ItemModel> {
        override fun createFromParcel(parcel: Parcel): ItemModel {
            return ItemModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemModel?> {
            return arrayOfNulls(size)
        }
    }
}
