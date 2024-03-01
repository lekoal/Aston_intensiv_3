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
        onDeleteItem: (List<ContactInfo>) -> Unit
    ) =
        adapterDelegateViewBinding<ContactInfo, ContactListItem, RvContactItemBinding>(
            { layoutInflater, parent ->
                RvContactItemBinding.inflate(layoutInflater, parent, false)
            }
        ) {
            bind { diffPayloads ->
                if (diffPayloads.isNotEmpty()) {
                    Log.i("diffPayloads", "GET PAYLOADS")
                    val newContent = diffPayloads.first() as Bundle
                    textViewAction(
                        binding.rvItemName,
                        newContent.getString(PAYLOADS_NAME_KEY, item.name)
                    )
                    textViewAction(
                        binding.rvItemSurname,
                        newContent.getString(PAYLOADS_SURNAME_KEY, item.surname)
                    )
                    textViewAction(
                        binding.rvItemPhone,
                        newContent.getString(PAYLOADS_PHONE_KEY, item.name)
                    )
                    if (newContent.getBoolean(PAYLOADS_CHECK_BOX_KEY))
                        binding.rvItemCheckBox.visibility = View.VISIBLE else View.GONE
                } else {
                    Log.i("diffPayloads", "DON'T GET PAYLOADS")
                    binding.rvItemId.text = item.id.toString()
                    binding.rvItemName.text = item.name
                    binding.rvItemSurname.text = item.surname
                    binding.rvItemPhone.text = item.phone
                    binding.rvItemCheckBox.visibility =
                        if (item.showCheckBox) View.VISIBLE else View.GONE
                }
                binding.root.setOnClickListener {
                    itemClickListener(item)
                }
                binding.rvItemCheckBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        checkedItems.add(item)
                    } else {
                        checkedItems.remove(item)
                    }
                }
                checkedItems.apply {
                    onDeleteItem(this)
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