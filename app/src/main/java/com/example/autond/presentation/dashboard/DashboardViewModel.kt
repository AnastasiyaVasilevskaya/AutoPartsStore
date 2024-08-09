package com.example.autond.presentation.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autond.domain.entity.CategoryModel
import com.example.autond.domain.entity.SliderModel
import com.example.autond.domain.entity.ItemModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardViewModel : ViewModel() {

    private val firebaseDB = FirebaseDatabase.getInstance()

    private val _banner = MutableLiveData<List<SliderModel>>()
    val banners: LiveData<List<SliderModel>> = _banner

    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    val categories: LiveData<MutableList<CategoryModel>> = _category

    private val _popular = MutableLiveData<MutableList<ItemModel>>()
    val popular: LiveData<MutableList<ItemModel>> = _popular

    fun loadBanners() {
        val ref = firebaseDB.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun loadCategories() {
        val ref = firebaseDB.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(CategoryModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _category.value = lists
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun loadRecommendations() {
        val ref = firebaseDB.getReference("Items")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemModel>()
                for (childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemModel::class.java)
                    if (list != null) {
                        lists.add(list)
                    }
                }
                _popular.value = lists
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}