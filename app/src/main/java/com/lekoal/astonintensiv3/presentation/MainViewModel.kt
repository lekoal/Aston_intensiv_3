package com.lekoal.astonintensiv3.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lekoal.astonintensiv3.model.ContactInfo
import com.lekoal.astonintensiv3.model.ContactsInitial
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _resultContacts = MutableStateFlow<List<ContactInfo>>(listOf())
    val resultContacts: StateFlow<List<ContactInfo>> get() = _resultContacts
    private val currentContacts = ContactsInitial.get().toMutableList()

    init {
        viewModelScope.launch {
            _resultContacts.emit(currentContacts)
        }
    }
    suspend fun deleteItems(items: List<ContactInfo>) {
        viewModelScope.launch {
            currentContacts.removeAll(items)
            _resultContacts.emit(
                currentContacts
            )
        }
    }

    suspend fun addItem(item: ContactInfo) {
        viewModelScope.launch {
            currentContacts.add(item)
            _resultContacts.emit(currentContacts)
        }
    }

    suspend fun editItem(item: ContactInfo) {
        viewModelScope.launch {
            currentContacts.removeAt(item.id - 1)
            currentContacts.add(item.id - 1, item)
            _resultContacts.emit(currentContacts)
        }
    }

    fun checkBoxChangeVisibility() {
        viewModelScope.launch {
            currentContacts.replaceAll {
                if (it.showCheckBox) it.copy(showCheckBox = false) else it.copy(showCheckBox = true)
            }
            _resultContacts.value = currentContacts
        }
    }
}