package com.lekoal.astonintensiv3.presentation

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.lekoal.astonintensiv3.R
import com.lekoal.astonintensiv3.databinding.ActivityMainBinding
import com.lekoal.astonintensiv3.model.ContactsAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactsRV: RecyclerView
    private lateinit var contactsAdapter: ContactsAdapter
    private var contactsListSize = 0
    private var isDeleteShows = false
    private lateinit var mainViewModel: MainViewModel
//    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.mainToolBar
        setSupportActionBar(toolbar)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
//        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        contactsRV = binding.rvContacts
        contactsAdapter = ContactsAdapter(
            onItemListener = {
                Log.i("onItemListener", "name: ${it.name}")
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
        contactsRV.adapter = contactsAdapter
        lifecycleScope.launch {
            mainViewModel.resultContacts.collect {
                contactsAdapter.items = it
//                contactsListSize = it.size
            }
        }
//        lifecycleScope.launch {
//            sharedViewModel.contact.collect {
//                if (it.name != "") {
//                    mainViewModel.addItem(it)
//                }
//            }
//        }
        activateButtons()
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

    private fun activateButtons() {
        binding.btnCancel.setOnClickListener {
            isDeleteShows = !isDeleteShows
            mainViewModel.cancelDeleting()
            showAddButton()
        }
//        binding.btnAddContact.setOnClickListener {
//            val editorDialogFragment = DialogEditorFragment.newInstance(
//                id = contactsListSize + 1,
//                name = "",
//                surname = "",
//                phone = ""
//            )
//            editorDialogFragment.show(supportFragmentManager, "editorDialog")
//        }
    }
}