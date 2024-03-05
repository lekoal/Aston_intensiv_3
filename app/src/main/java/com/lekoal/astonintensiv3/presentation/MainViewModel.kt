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
            _resultContacts.value = newList.toList()
            oldList = newList
        }
    }

    fun addItem(item: ContactInfo) {
        viewModelScope.launch {
            newList = oldList
            val size = newList.size
            if (!newList.contains(item)) {
                newList.add(size, item)
            }
            _resultContacts.value = newList.toList()
            oldList = newList
        }
    }

    fun cancelDeleting() {
        viewModelScope.launch {
            newList = oldList.map {
                it.copy(isChecked = false, showCheckBox = false)
            }.toMutableList()
            _resultContacts.value = newList.toList()
            oldList = newList
        }
    }

    fun editItem(item: ContactInfo) {
        viewModelScope.launch {
            newList = oldList.map {
                if (it.id == item.id) item else it
            }.toMutableList()
            _resultContacts.value = newList.toList()
            oldList = newList
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
                if (it.id == item.id) item.copy(isChecked = !item.isChecked) else it
            }.toMutableList()
            _resultContacts.value = newList.toList()
            oldList = newList
        }
    }

    fun updateList(updatedList: List<ContactInfo>) {
        viewModelScope.launch {
            newList = updatedList.toMutableList()
            _resultContacts.value = newList.toList()
            oldList = newList
        }
    }
}