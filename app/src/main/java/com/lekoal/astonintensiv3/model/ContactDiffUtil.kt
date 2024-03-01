package com.lekoal.astonintensiv3.model

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.lekoal.astonintensiv3.domain.ContactListItem

const val PAYLOADS_NAME_KEY = "PAYLOADS_NAME_KEY"
const val PAYLOADS_SURNAME_KEY = "PAYLOADS_SURNAME_KEY"
const val PAYLOADS_PHONE_KEY = "PAYLOADS_PHONE_KEY"
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
            diffBundle.putString(PAYLOADS_NAME_KEY, newItem.name)
        }
        if (oldItem.surname != newItem.surname) {
            diffBundle.putString(PAYLOADS_SURNAME_KEY, newItem.surname)
        }
        if (oldItem.phone != newItem.phone) {
            diffBundle.putString(PAYLOADS_PHONE_KEY, newItem.phone)
        }
        if (diffBundle.size() == 0) {
            return null
        }
        return diffBundle
    }
}