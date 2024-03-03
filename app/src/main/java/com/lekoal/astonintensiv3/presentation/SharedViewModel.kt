package com.lekoal.astonintensiv3.presentation

import androidx.lifecycle.ViewModel
import com.lekoal.astonintensiv3.model.ContactInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _contact = MutableStateFlow(ContactInfo(
        id = 0,
        name = "",
        surname = "",
        phone = ""
    ))
    val contact: StateFlow<ContactInfo> get() = _contact

    fun sendContact(id: Int, name: String, surname: String, phone: String) {
        _contact.value = ContactInfo(id, name, surname, phone)
    }
}