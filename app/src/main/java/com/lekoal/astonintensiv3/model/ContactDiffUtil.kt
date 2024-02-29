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
}