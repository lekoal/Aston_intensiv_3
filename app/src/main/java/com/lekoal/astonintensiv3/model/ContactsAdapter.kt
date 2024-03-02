package com.lekoal.astonintensiv3.model

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.lekoal.astonintensiv3.domain.ContactListItem

class ContactsAdapter(
    onItemListener: (ContactInfo) -> Unit,
    onDeleteItem: (List<ContactInfo>) -> Unit,
    onCheckItem: (ContactInfo) -> Unit
) :
    AsyncListDifferDelegationAdapter<ContactListItem>(ContactDiffUtil()) {
    init {
        delegatesManager.addDelegate(
            MainActivityDelegates.contactsDelegate(
                onItemListener,
                onDeleteItem,
                onCheckItem
            )
        )
    }
}