package com.example.school.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.school.domain.Item


class DetailViewModel(item: Item,
                      app: Application) : AndroidViewModel(app) {

    private val _selectedItem = MutableLiveData<Item>()

    val selectedItem: LiveData<Item>
        get() = _selectedItem

    init {
        _selectedItem.value = item
    }

}

