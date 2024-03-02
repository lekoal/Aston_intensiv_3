package com.lekoal.astonintensiv3.model

import com.lekoal.astonintensiv3.domain.ContactListItem

data class ContactInfo(
    val id: Int,
    val name: String,
    val surname: String,
    val phone: String,
    val showCheckBox: Boolean = false
) : ContactListItem
