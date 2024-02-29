package com.lekoal.astonintensiv3.model

import com.lekoal.astonintensiv3.domain.ContactListItem

data class ContactInfo(
    override val id: Int,
    override val name: String,
    override val surname: String,
    override val phone: String
) : ContactListItem
