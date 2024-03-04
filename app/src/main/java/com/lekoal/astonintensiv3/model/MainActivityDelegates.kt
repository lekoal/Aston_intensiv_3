package com.lekoal.astonintensiv3.model

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.lekoal.astonintensiv3.databinding.RvContactItemBinding
import com.lekoal.astonintensiv3.domain.ContactListItem

object MainActivityDelegates {
    private val checkedItems = mutableListOf<ContactInfo>()
    fun contactsDelegate(
        itemClickListener: (ContactInfo) -> Unit,
        onDeleteItem: (List<ContactInfo>) -> Unit,
        onCheckItem: (ContactInfo) -> Unit,
        onCheckedListChange: (List<ContactInfo>) -> Unit
    ) =
        adapterDelegateViewBinding<ContactInfo, ContactListItem, RvContactItemBinding>({ layoutInflater, parent ->
            RvContactItemBinding.inflate(layoutInflater, parent, false)
        }) {
            bind { diffPayloads ->
                if (diffPayloads.isNotEmpty() && diffPayloads.first() is Bundle) {
                    val newContent = diffPayloads.first() as Bundle
                    payloadsBinding(newContent, binding, item)
                } else {
                    defaultBinding(binding, item)
                }
                if (binding.rvItemCheckBox.visibility == View.GONE) {
                    binding.root.isClickable = true
                    binding.root.setOnClickListener {
                        itemClickListener(item)
                    }
                }
                if (binding.rvItemCheckBox.visibility == View.VISIBLE) {
                    binding.root.isClickable = false
                }
                listForDeleteFill(binding, onCheckItem, onDeleteItem, item, onCheckedListChange)
            }
        }

    private fun textViewAction(view: AppCompatTextView, newText: String) {
        view.animate().alpha(0f).setDuration(300).withEndAction {
                view.text = newText
                view.animate().alpha(1f).setDuration(300)
            }
    }

    private fun payloadsBinding(
        newContent: Bundle, binding: RvContactItemBinding, item: ContactListItem
    ) {
        when {
            newContent.containsKey(PAYLOADS_NAME_KEY) -> {
                textViewAction(
                    binding.rvItemName, newContent.getString(PAYLOADS_NAME_KEY, item.name)
                )
            }
            newContent.containsKey(PAYLOADS_SURNAME_KEY) -> {
                textViewAction(
                    binding.rvItemSurname, newContent.getString(PAYLOADS_SURNAME_KEY, item.surname)
                )
            }
            newContent.containsKey(PAYLOADS_PHONE_KEY) -> {
                textViewAction(
                    binding.rvItemPhone, newContent.getString(PAYLOADS_PHONE_KEY, item.name)
                )
            }
            newContent.containsKey(PAYLOADS_CHECK_BOX_KEY) -> {
                if (newContent.getBoolean(PAYLOADS_CHECK_BOX_KEY)) {
                    binding.rvItemCheckBox.visibility = View.VISIBLE
                    binding.root.isClickable = false
                } else {
                    binding.rvItemCheckBox.visibility = View.GONE
                    binding.root.isClickable = true
                }
            }
            newContent.containsKey(PAYLOADS_IS_CHECKED_KEY) -> {
                binding.rvItemCheckBox.isChecked = newContent.getBoolean(PAYLOADS_IS_CHECKED_KEY)
            }
        }
    }

    private fun defaultBinding(binding: RvContactItemBinding, item: ContactListItem) {
        binding.rvItemId.text = item.id.toString()
        binding.rvItemName.text = item.name
        binding.rvItemSurname.text = item.surname
        binding.rvItemPhone.text = item.phone
        binding.rvItemCheckBox.visibility = if (item.showCheckBox) View.VISIBLE else View.GONE
        binding.rvItemCheckBox.isChecked = item.isChecked
    }

    private fun listForDeleteFill(
        binding: RvContactItemBinding,
        onCheckItem: (ContactInfo) -> Unit,
        onDeleteItem: (List<ContactInfo>) -> Unit,
        item: ContactInfo,
        onCheckedListChange: (List<ContactInfo>) -> Unit
    ) {
        binding.rvItemCheckBox.setOnClickListener {
            onCheckItem(item)
            if (binding.rvItemCheckBox.isChecked) {
                if (!checkedItems.contains(item)) {
                    checkedItems.add(item)
                    onCheckedListChange(checkedItems)
                }
            }
            if (!binding.rvItemCheckBox.isChecked) {
                checkedItems.removeIf { it.id == item.id }
                onCheckedListChange(checkedItems)
            }
            onDeleteItem(checkedItems)
        }
    }
}