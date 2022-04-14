package com.example.cvstest.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cvstest.R
import com.example.cvstest.databinding.ActivityMainBinding
import com.example.cvstest.viewmodel.MainViewModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private val imagesAdapter = ImagesAdapter()
    private val viewModel: MainViewModel by viewModels()
    private var listItems: ArrayList<String> = arrayListOf()
    private lateinit var listAdapter: ArrayAdapter<*>


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
        setObservers()
    }

    private fun setObservers() {
        viewModel.imageList.observe(this) {
            with(imagesAdapter) {
                images.clear()
                images.addAll(it.items)
                addData(it.items)
                notifyDataSetChanged()
            }
        }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpViews() {
        binding.searchLayout.searchView.setOnQueryTextListener(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = imagesAdapter
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems)
        binding.searchLayout.listView.adapter = listAdapter

        showListView()

        binding.searchLayout.listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, i, id ->
                val item = binding.searchLayout.listView.adapter.getItem(i).toString()
                binding.searchLayout.searchView.setQuery(item, true)
            }

        binding.searchLayout.searchView.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                binding.searchLayout.listLayout.visibility = View.VISIBLE
            } else {
                binding.searchLayout.listLayout.visibility = View.GONE
                binding.searchLayout.searchView.clearFocus()
            }
        }
        imagesAdapter.setOnItemClickListener {
            val sharedPrefEditor = getSharedPreferences("ITEM_DATA", Context.MODE_PRIVATE).edit()
            val gson = Gson()
            val string = gson.toJson(it)
            sharedPrefEditor.putString("ITEM_TYPE", string)
            sharedPrefEditor.apply()
            val intent = Intent(this@MainActivity, ImageViewActivity::class.java)
            startActivity(intent)
        }
    }


    private fun showListView() {
        if (listItems.size <= 0)
            binding.searchLayout.tvSearchHeader.text = getString(R.string.recent_search_empty)
        else {
            binding.searchLayout.tvSearchHeader.text = getString(R.string.recent_search)
        }
        listAdapter.notifyDataSetChanged()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        imagesAdapter.filter.filter(query)
        query?.let {
            if (!listItems.contains(it)) {
                if (listItems.size <= 4) {
                    listItems.add(it)
                } else {
                    listItems.removeAt(0)
                    listItems.add(it)
                }
                showListView()
            }
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty()) {
            imagesAdapter.filter.filter(newText)
            binding.searchLayout.listLayout.visibility = View.GONE
            binding.searchLayout.searchView.clearFocus()
        }
        return false
    }
}