package com.lekoal.astonintensiv3.model

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.lekoal.astonintensiv3.domain.ContactListItem
import com.lekoal.astonintensiv3.domain.ItemTouchHelperAdapter
import com.lekoal.astonintensiv3.domain.OnListChangedListener

class ContactsAdapter(
    onItemListener: (ContactInfo) -> Unit,
    onDeleteItem: (List<ContactInfo>) -> Unit,
    onCheckItem: (ContactInfo) -> Unit,
    onCheckedListChange: (List<ContactInfo>) -> Unit
) :
    AsyncListDifferDelegationAdapter<ContactListItem>(ContactDiffUtil()), ItemTouchHelperAdapter {
    private var listChangedListener: OnListChangedListener? = null

    init {
        delegatesManager.addDelegate(
            MainActivityDelegates.contactsDelegate(
                onItemListener,
                onDeleteItem,
                onCheckItem,
                onCheckedListChange
            )
        )
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val currentItems = items.toMutableList()
        currentItems.swap(fromPosition, toPosition)
        items = currentItems.toList()
        listChangedListener?.onListChanged(currentItems.mapNotNull {
            it as? ContactInfo
        })
    }

    override fun getCurrentItems(): MutableList<ContactListItem> = items

    private fun <T> MutableList<T>.swap(fromPosition: Int, toPosition: Int) {
        if (fromPosition == toPosition) return
        val item = this.removeAt(fromPosition)
        this.add(toPosition, item)
    }

    fun setOnListChangedListener(listener: OnListChangedListener) {
        listChangedListener = listener
    }

}