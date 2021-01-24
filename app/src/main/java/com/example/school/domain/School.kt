package com.example.school.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class School(
    val website: String?,
    val roepnaam: String?,
    val naam: String,
    val adres: String?,
    val email: String?,
    val telefoon: String?
) : Parcelable
