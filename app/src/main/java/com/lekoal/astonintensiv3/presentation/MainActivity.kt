package com.lekoal.astonintensiv3.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.lekoal.astonintensiv3.R
import com.lekoal.astonintensiv3.databinding.ActivityMainBinding
import com.lekoal.astonintensiv3.model.ContactsAdapter
import com.lekoal.astonintensiv3.model.ContactsInitial

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contactsRV: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar = binding.mainToolBar
        setSupportActionBar(toolbar)

        val contactsAdapter = ContactsAdapter {
            Toast.makeText(this, "Click ${it.name}", Toast.LENGTH_SHORT).show()
        }

        contactsRV = binding.rvContacts

        contactsRV.adapter = contactsAdapter
        contactsAdapter.items = ContactsInitial.get()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this, "Menu selected!", Toast.LENGTH_SHORT).show()
        return true
    }
}