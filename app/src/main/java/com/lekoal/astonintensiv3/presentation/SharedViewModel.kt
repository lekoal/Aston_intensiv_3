package com.lekoal.astonintensiv3.presentation

import androidx.lifecycle.ViewModel
import com.lekoal.astonintensiv3.model.ContactInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val initialContact =
        ContactInfo(
            id = 0,
            name = "",
            surname = "",
            phone = ""
        )

    private val _addContact = MutableStateFlow(initialContact)
    val addContact: StateFlow<ContactInfo> get() = _addContact

    private val _editContact = MutableStateFlow(initialContact)
    val editContact: StateFlow<ContactInfo> get() = _editContact

    fun sendAddContact(id: Int, name: String, surname: String, phone: String) {
        _addContact.value = ContactInfo(id, name, surname, phone)
    }

    fun sendEditContact(id: Int, name: String, surname: String, phone: String) {
        _editContact.value = ContactInfo(id, name, surname, phone)
    }
}