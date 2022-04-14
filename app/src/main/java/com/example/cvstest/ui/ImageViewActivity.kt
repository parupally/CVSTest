package com.example.cvstest.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cvstest.databinding.ActivityImageviewBinding
import com.example.cvstest.model.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ImageViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        updateViews()
    }

    private fun updateViews() {
        val gson = Gson()
        val sharedPref: SharedPreferences = getSharedPreferences("ITEM_DATA", Context.MODE_PRIVATE)
        val json = sharedPref.getString("ITEM_TYPE", "")
        val type = object : TypeToken<Item>() {}.type
        val itemData = gson.fromJson(json, type) as Item


        Glide.with(this)
            .load(itemData.media.m)
            .into(binding.ivFullImage).getSize { width, height ->
                binding.tvHeightValue.text = height.toString()
                binding.tvWidthValue.text = width.toString()
            }

        binding.tvTitleValue.text = itemData.title
        binding.tvAuthorValue.text = itemData.author
        binding.tvDescriptionValue.text = itemData.description
    }
}
