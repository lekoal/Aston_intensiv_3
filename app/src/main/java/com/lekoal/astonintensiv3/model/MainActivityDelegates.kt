package com.lekoal.astonintensiv3.model

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lekoal.astonintensiv3.databinding.RvContactItemBinding
import com.lekoal.astonintensiv3.domain.ContactListItem

object MainActivityDelegates {
    fun contactsDelegate(itemClickListener: (ContactInfo) -> Unit) =
        adapterDelegateViewBinding<ContactInfo, ContactListItem, RvContactItemBinding>(
            { layoutInflater, parent ->
                RvContactItemBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            binding.root.setOnClickListener {
                itemClickListener(item)
            }
            bind { diffPayloads ->
                binding.rvItemId.text = item.id.toString()
                binding.rvItemName.text = item.name
                binding.rvItemSurname.text = item.surname
                binding.rvItemPhone.text = item.phone
            }
        }
}