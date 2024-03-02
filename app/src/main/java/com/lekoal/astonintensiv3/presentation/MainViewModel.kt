package com.lekoal.astonintensiv3.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lekoal.astonintensiv3.model.ContactInfo
import com.lekoal.astonintensiv3.model.ContactsInitial
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var oldList = ContactsInitial.get().toMutableList()
    private var newList = mutableListOf<ContactInfo>()
    private val _resultContacts = MutableStateFlow<List<ContactInfo>>(oldList)
    val resultContacts: StateFlow<List<ContactInfo>> get() = _resultContacts

    fun deleteItems(items: List<ContactInfo>) {
        viewModelScope.launch {
            oldList.removeAll(items)
        }
    }
    init {
        newList = oldList
    }

    fun addItem(item: ContactInfo) {
        viewModelScope.launch {
            oldList.add(item)
        }
    }

    fun editItem(item: ContactInfo) {
        viewModelScope.launch {
            oldList.removeAt(item.id - 1)
            oldList.add(item.id - 1, item)
        }
    }

    fun checkBoxChangeVisibility() {
        newList = oldList.map {
            it.copy(showCheckBox = !it.showCheckBox)
        }.toMutableList()
        _resultContacts.value = newList.toList()
        oldList = newList
    }
}