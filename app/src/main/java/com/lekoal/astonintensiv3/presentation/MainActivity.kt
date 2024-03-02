package com.lekoal.astonintensiv3.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lekoal.astonintensiv3.R
import com.lekoal.astonintensiv3.databinding.ActivityMainBinding
import com.lekoal.astonintensiv3.model.ContactInfo
import com.lekoal.astonintensiv3.model.ContactsListAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactsRV: RecyclerView
    private lateinit var contactsAdapter: ListAdapter<ContactInfo, ContactsListAdapter.ContactsViewHolder>
    private var isDeleteShows = false
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.mainToolBar
        setSupportActionBar(toolbar)
        contactsRV = binding.rvContacts

        contactsAdapter = ContactsListAdapter()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        contactsRV.adapter = contactsAdapter

        lifecycleScope.launch {
            viewModel.resultContacts.collect {
                contactsAdapter.submitList(it)
            }
        }

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
        viewModel.checkBoxChangeVisibility()
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
}