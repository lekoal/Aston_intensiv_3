package com.lekoal.astonintensiv3.domain

interface ContactListItem {
    val id: Int
    val name: String
    val surname: String
    val phone: String
    val showCheckBox: Boolean
    val isChecked: Boolean
    override fun equals(other: Any?): Boolean
}