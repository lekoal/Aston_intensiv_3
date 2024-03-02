package com.lekoal.astonintensiv3.model

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

const val PAYLOADS_NAME_KEY = "PAYLOADS_NAME_KEY"
const val PAYLOADS_SURNAME_KEY = "PAYLOADS_SURNAME_KEY"
const val PAYLOADS_PHONE_KEY = "PAYLOADS_PHONE_KEY"
const val PAYLOADS_CHECK_BOX_KEY = "PAYLOADS_CHECK_BOX_KEY"

class ContactDiffUtil : DiffUtil.ItemCallback<ContactInfo>() {
    override fun areItemsTheSame(oldItem: ContactInfo, newItem: ContactInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ContactInfo, newItem: ContactInfo): Boolean {
        return (oldItem.name == newItem.name &&
                oldItem.surname == newItem.surname &&
                oldItem.phone == newItem.phone &&
                oldItem.showCheckBox == newItem.showCheckBox)
    }

    override fun getChangePayload(oldItem: ContactInfo, newItem: ContactInfo): Any? {
        val payload = Bundle()
        return when {
            oldItem.name != newItem.name -> {
                payload.putString(PAYLOADS_NAME_KEY, newItem.name)
            }
            oldItem.surname != newItem.surname -> {
                payload.putString(PAYLOADS_SURNAME_KEY, newItem.surname)
            }
            oldItem.phone != newItem.phone -> {
                payload.putString(PAYLOADS_PHONE_KEY, newItem.phone)
            }
            oldItem.showCheckBox != newItem.showCheckBox -> {
                payload.putBoolean(PAYLOADS_CHECK_BOX_KEY, newItem.showCheckBox)
            }
            else -> {
                super.getChangePayload(oldItem, newItem)
            }
        }
    }
}