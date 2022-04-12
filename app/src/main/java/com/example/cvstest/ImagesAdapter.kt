package com.example.cvstest

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cvstest.databinding.ImageAdapterBinding
import kotlinx.android.synthetic.main.image_adapter.view.*

class ImagesAdapter (val images: MutableList<Item> = mutableListOf()): RecyclerView.Adapter<ImagesAdapter.ViewHolder>() ,
    Filterable {


    var imageListFiltered: MutableList<Item> = mutableListOf()

    inner class ViewHolder(private val binding: ImageAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageResponse: Item) {
            binding.title.text =imageResponse.title
            Glide.with(binding.imageView).load(imageResponse.media.m).into(binding.imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ImageAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageListFiltered[position])
    }

    override fun getItemCount(): Int {
        return imageListFiltered.size
    }
    fun addData(list: List<Item>) {
        imageListFiltered = list as MutableList<Item>
        notifyDataSetChanged()
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) imageListFiltered = images else {
                    val filteredList = ArrayList<Item>()
                    images
                        .filter {
                            (it.title.lowercase().contains(constraint!!)) or
                                    (it.author.lowercase().contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    imageListFiltered = filteredList

                }
                return FilterResults().apply { values = imageListFiltered }
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                imageListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Item>
                notifyDataSetChanged()

            }
        }
    }
}