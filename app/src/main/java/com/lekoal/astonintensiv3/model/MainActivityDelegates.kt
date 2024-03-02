package com.lekoal.astonintensiv3.model

import android.os.Bundle
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
        onCheckItem: (ContactInfo) -> Unit
    ) =
        adapterDelegateViewBinding<ContactInfo, ContactListItem, RvContactItemBinding>(
            { layoutInflater, parent ->
                RvContactItemBinding.inflate(layoutInflater, parent, false)
            }
        ) {

            bind { diffPayloads ->

                if (diffPayloads.isNotEmpty() && diffPayloads.first() is Bundle) {
                    val newContent = diffPayloads.first() as Bundle
                    when {
                        newContent.containsKey(PAYLOADS_NAME_KEY) -> {
                            textViewAction(
                                binding.rvItemName,
                                newContent.getString(PAYLOADS_NAME_KEY, item.name)
                            )
                        }

                        newContent.containsKey(PAYLOADS_SURNAME_KEY) -> {
                            textViewAction(
                                binding.rvItemSurname,
                                newContent.getString(PAYLOADS_SURNAME_KEY, item.surname)
                            )
                        }

                        newContent.containsKey(PAYLOADS_PHONE_KEY) -> {
                            textViewAction(
                                binding.rvItemPhone,
                                newContent.getString(PAYLOADS_PHONE_KEY, item.name)
                            )
                        }

                        newContent.containsKey(PAYLOADS_CHECK_BOX_KEY) -> {
                            if (newContent.getBoolean(PAYLOADS_CHECK_BOX_KEY))
                                binding.rvItemCheckBox.visibility = View.VISIBLE else View.GONE
                        }

                        newContent.containsKey(PAYLOADS_IS_CHECKED_KEY) -> {
                            binding.rvItemCheckBox.isChecked =
                                newContent.getBoolean(PAYLOADS_IS_CHECKED_KEY)
                        }
                    }
                } else {
                    binding.rvItemId.text = item.id.toString()
                    binding.rvItemName.text = item.name
                    binding.rvItemSurname.text = item.surname
                    binding.rvItemPhone.text = item.phone
                    binding.rvItemCheckBox.visibility =
                        if (item.showCheckBox) View.VISIBLE else View.GONE
                    binding.rvItemCheckBox.isChecked = item.isChecked
                }

                binding.root.setOnClickListener {
                    itemClickListener(item)
                }

                binding.rvItemCheckBox.setOnClickListener {
                    onCheckItem(item)

                    if (binding.rvItemCheckBox.isChecked) {
                        if (!checkedItems.contains(item)) {
                            checkedItems.add(item)
                        }
                    }
                    if (!binding.rvItemCheckBox.isChecked) {
                        checkedItems.removeIf { it.id == item.id }
                    }
                    onDeleteItem(checkedItems)
                }
            }
        }

    private fun textViewAction(view: AppCompatTextView, newText: String) {
        view.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                view.text = newText
                view.animate()
                    .alpha(1f)
                    .setDuration(300)
            }
    }
}