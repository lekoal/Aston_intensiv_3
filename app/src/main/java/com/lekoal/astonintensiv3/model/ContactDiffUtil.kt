package com.lekoal.astonintensiv3.model

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.lekoal.astonintensiv3.domain.ContactListItem

class ContactDiffUtil : DiffUtil.ItemCallback<ContactListItem>() {
    override fun areItemsTheSame(oldItem: ContactListItem, newItem: ContactListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactListItem, newItem: ContactListItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: ContactListItem, newItem: ContactListItem): Any? {
        val diffBundle = Bundle()
        if (oldItem.name != newItem.name) {
            diffBundle.putString("name", newItem.name)
        }
        if (oldItem.surname != newItem.surname) {
            diffBundle.putString("surname", newItem.surname)
        }
        if (oldItem.phone != newItem.phone) {
            diffBundle.putString("phone", newItem.phone)
        }
        if (diffBundle.size() == 0) {
            return null
        }
        return diffBundle
    }
}