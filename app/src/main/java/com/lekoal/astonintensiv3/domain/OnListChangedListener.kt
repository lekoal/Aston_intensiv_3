package com.lekoal.astonintensiv3.domain

import com.lekoal.astonintensiv3.model.ContactInfo

interface OnListChangedListener {
    fun onListChanged(updatedList: List<ContactInfo>)
}