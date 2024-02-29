package com.lekoal.astonintensiv3.model

data class ContactInfo(
    val id: Int,
    val name: String,
    val surname: String,
    val phone: String
) : ContactListItem
