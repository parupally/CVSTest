package com.example.cvstest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Media(
    val m: String
): Parcelable
