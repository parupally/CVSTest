package com.example.cvstest

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cvstest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    android.widget.SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private val imagesAdapter = ImagesAdapter()
    private val viewModel: MainViewModel by viewModels()

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
    }

    private fun setUpViews() {
        binding.searchLayout.searchView.setOnQueryTextListener(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = imagesAdapter

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        imagesAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        imagesAdapter.filter.filter(newText)
        return false

    }
}