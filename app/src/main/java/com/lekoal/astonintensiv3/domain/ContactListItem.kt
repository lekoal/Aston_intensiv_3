package com.lekoal.astonintensiv3.domain

interface ContactListItem {
    val id: Int
    val name: String
    val surname: String
    val phone: String
    override fun equals(other: Any?): Boolean
}