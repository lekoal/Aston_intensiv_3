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

    init {
        newList = oldList
    }

    fun deleteItems(items: List<ContactInfo>) {
        viewModelScope.launch {
            newList = oldList
            items.forEach { item ->
                newList.removeIf {
                    item.id == it.id
                }
            }
            _resultContacts.value = newList
            oldList = newList
        }
    }

    suspend fun addItem(item: ContactInfo) {
        viewModelScope.launch {
            oldList.add(item)
            _resultContacts.emit(oldList)
        }
    }

    suspend fun editItem(item: ContactInfo) {
        viewModelScope.launch {
            oldList.removeAt(item.id - 1)
            oldList.add(item.id - 1, item)
            _resultContacts.emit(oldList)
        }
    }

    fun checkBoxChangeVisibility() {
        viewModelScope.launch {
            newList = oldList.map {
                it.copy(showCheckBox = !it.showCheckBox)
            }.toMutableList()
            _resultContacts.value = newList.toList()
            oldList = newList
        }
    }

    fun changeCheckItem(item: ContactInfo) {
        viewModelScope.launch {
            newList = oldList.map {
                if (it == item) item.copy(isChecked = !item.isChecked) else it
            }.toMutableList()
            _resultContacts.value = newList.toList()
            oldList = newList
        }
    }
}