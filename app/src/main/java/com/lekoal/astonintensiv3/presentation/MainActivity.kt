package com.lekoal.astonintensiv3.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.lekoal.astonintensiv3.R
import com.lekoal.astonintensiv3.databinding.ActivityMainBinding
import com.lekoal.astonintensiv3.domain.OnListChangedListener
import com.lekoal.astonintensiv3.model.ContactInfo
import com.lekoal.astonintensiv3.model.ContactsAdapter
import com.lekoal.astonintensiv3.model.SimpleItemTouchHelperCallback
import kotlinx.coroutines.launch

private const val IS_DELETE_SHOWS = "IS_DELETE_SHOWS"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactsRV: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter
    private var contactsMaxId = 0
    private var isDeleteShows = false
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.mainToolBar
        setSupportActionBar(toolbar)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        contactsRV = binding.rvContacts
        setAdapter()
        contactsRV.adapter = contactsAdapter
        createItemTouchHelper()
        viewModelsOperations()
        activateButtons()
        setOnMoveChangeListListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        isDeleteShows = !isDeleteShows
        if (isDeleteShows) {
            hideAddButton()
        } else {
            showAddButton()
        }
        mainViewModel.checkBoxChangeVisibility()
        return true
    }

    private fun hideAddButton() {
        binding.btnAddContact.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                binding.btnAddContact.visibility = View.GONE
                binding.btnCancel.visibility = View.VISIBLE
                binding.btnDelete.visibility = View.VISIBLE
            }
        binding.btnCancel.animate()
            .alpha(1f)
            .setDuration(300)
        binding.btnDelete.animate()
            .alpha(1f)
            .setDuration(300)
    }

    private fun showAddButton() {
        binding.btnCancel.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                binding.btnCancel.visibility = View.GONE
                binding.btnAddContact.visibility = View.VISIBLE
            }
        binding.btnDelete.animate()
            .alpha(0f)
            .setDuration(300)
            .withEndAction {
                binding.btnDelete.visibility = View.GONE
            }
        binding.btnAddContact.animate()
            .alpha(1f)
            .setDuration(300)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_DELETE_SHOWS, isDeleteShows)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setOnRestoreApplication(savedInstanceState)

    }

    private fun activateButtons() {
        binding.btnCancel.setOnClickListener {
            isDeleteShows = !isDeleteShows
            mainViewModel.cancelDeleting()
            showAddButton()
        }
        binding.btnAddContact.setOnClickListener {
            val editorDialogFragment = DialogEditorFragment.newInstance(
                id = contactsMaxId + 1,
                name = "",
                surname = "",
                phone = ""
            )
            editorDialogFragment.show(supportFragmentManager, "editorDialogAdd")
        }
    }

    private fun setAdapter() {
        contactsAdapter = ContactsAdapter(
            onItemListener = {
                val editorDialogFragment = DialogEditorFragment.newInstance(
                    id = it.id,
                    name = it.name,
                    surname = it.surname,
                    phone = it.phone
                )
                editorDialogFragment.show(supportFragmentManager, "editorDialogEdit")
            },
            onDeleteItem = { contacts ->
                binding.btnDelete.setOnClickListener {
                    mainViewModel.deleteItems(contacts)
                }
            },
            onCheckItem = {
                mainViewModel.changeCheckItem(it)
            }
        )
    }

    private fun setOnRestoreApplication(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            isDeleteShows = savedInstanceState.getBoolean(IS_DELETE_SHOWS, false)
            if (isDeleteShows) {
                hideAddButton()
            }
        }
    }

    private fun viewModelsOperations() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.resultContacts.collect {
                    contactsAdapter.items = it
                    contactsMaxId = it.last().id
                }
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.addContact.collect {
                    if (it.name != "") {
                        mainViewModel.addItem(it)
                        sharedViewModel.clearAllLists()
                    }
                }
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sharedViewModel.editContact.collect {
                    if (it.name != "") {
                        mainViewModel.editItem(it)
                        sharedViewModel.clearAllLists()
                    }
                }
            }
        }
    }

    private fun createItemTouchHelper() {
        val itemTouchCallback = SimpleItemTouchHelperCallback(contactsAdapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvContacts)
    }

    private fun setOnMoveChangeListListener() {
        contactsAdapter.setOnListChangedListener(object : OnListChangedListener {
            override fun onListChanged(updatedList: List<ContactInfo>) {
                mainViewModel.updateList(updatedList)
            }
        })
    }
}