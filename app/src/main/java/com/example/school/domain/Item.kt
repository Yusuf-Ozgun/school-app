package com.example.school.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val datasetid: String,
    val recordid: String,
    val record_timestamp: String,
    val fields: School
) : Parcelable