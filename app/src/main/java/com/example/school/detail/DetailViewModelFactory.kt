package com.example.school.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.school.domain.Item


class DetailViewModelFactory(
    private val item: Item,
    private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(item, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
