package com.example.cvstest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cvstest.databinding.ImageAdapterBinding
import com.example.cvstest.model.Item

class ImagesAdapter (val images: MutableList<Item> = mutableListOf()): RecyclerView.Adapter<ImagesAdapter.ViewHolder>() ,
    Filterable {


    var imageListFiltered: MutableList<Item> = mutableListOf()


    private var onItemClick: ((Item) -> Unit)? = null

    fun setOnItemClickListener(item: (Item) -> Unit) {
        onItemClick = item
    }
    inner class ViewHolder(val binding: ImageAdapterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageResponse: Item) {
            binding.title.text =imageResponse.title
            Glide.with(binding.imageView).load(imageResponse.media.m).into(binding.imageView)
        }
        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(imageListFiltered[adapterPosition])
            }
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
                val filteredList = ArrayList<Item>()
                if (constraint?.toString()!!.contains(",")){
                    val searchChar = constraint.toString().split(",")
                    for(charValue in searchChar){
                        if(charValue.isNotEmpty())
                        checkFilter(charValue,charValue,filteredList)
                    }
                } else {
                    val charString = constraint.toString() ?: ""
                    checkFilter(charString, constraint,filteredList)
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
    fun checkFilter(charString: String, constraint: CharSequence, filteredList: ArrayList<Item>) {
        imageListFiltered = if (charString.isEmpty())
            images else {
            images
                .filter {
                    (it.title.lowercase().contains(constraint!!)) or
                            (it.author.lowercase().contains(constraint))

                }
                .forEach { filteredList.add(it) }
            filteredList

        }
    }
}