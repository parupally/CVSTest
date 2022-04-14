package com.example.cvstest.model

import android.os.Parcelable
import com.example.cvstest.model.Item
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageResponse(
    val title: String,
    val link: String,
    val description: String,
    val modified: String,
    val generator: String,
    val items: List<Item>
): Parcelable

