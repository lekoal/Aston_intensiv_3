package com.lekoal.astonintensiv3.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lekoal.astonintensiv3.R

class ContactsListAdapter :
    ListAdapter<ContactInfo, ContactsListAdapter.ContactsViewHolder>(
        AsyncDifferConfig
            .Builder(ContactDiffUtil())
            .build()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        return ContactsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_contact_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int, payloads: List<Any>) {
        if (payloads.isNotEmpty() && payloads.first() is Bundle) {
            val newContent = payloads.first() as Bundle
            holder.updateContent(newContent)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val idTextView: AppCompatTextView = itemView.findViewById(R.id.rv_item_id)
        private val nameTextView: AppCompatTextView = itemView.findViewById(R.id.rv_item_name)
        private val surnameTextView: AppCompatTextView = itemView.findViewById(R.id.rv_item_surname)
        private val phoneTextView: AppCompatTextView = itemView.findViewById(R.id.rv_item_phone)
        private val selectCheckBox: CheckBox = itemView.findViewById(R.id.rv_item_check_box)

        fun bind(contact: ContactInfo) {
            idTextView.text = contact.id.toString()
            nameTextView.text = contact.name
            surnameTextView.text = contact.surname
            phoneTextView.text = contact.phone
            selectCheckBox.visibility = if (contact.showCheckBox) View.VISIBLE else View.GONE
        }

        fun updateContent(bundle: Bundle) {
            when {
                bundle.containsKey(PAYLOADS_NAME_KEY) -> {
                    nameTextView.text = bundle.getString(PAYLOADS_NAME_KEY)
                }

                bundle.containsKey(PAYLOADS_SURNAME_KEY) -> {
                    surnameTextView.text = bundle.getString(PAYLOADS_SURNAME_KEY)
                }

                bundle.containsKey(PAYLOADS_PHONE_KEY) -> {
                    phoneTextView.text = bundle.getString(PAYLOADS_PHONE_KEY)
                }

                bundle.containsKey(PAYLOADS_CHECK_BOX_KEY) -> {
                    selectCheckBox.visibility =
                        if (bundle.getBoolean(PAYLOADS_CHECK_BOX_KEY)) View.VISIBLE else View.GONE
                }
            }
        }

    }
}