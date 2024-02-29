package com.lekoal.astonintensiv3.model

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.lekoal.astonintensiv3.domain.ContactListItem

class ContactsAdapter(private val onItemClickListener: (ContactInfo) -> Unit) :
    AsyncListDifferDelegationAdapter<ContactListItem>(ContactDiffUtil()) {
    init {
        delegatesManager.addDelegate(MainActivityDelegates.contactsDelegate {
            onItemClickListener(it)
        })
    }
}