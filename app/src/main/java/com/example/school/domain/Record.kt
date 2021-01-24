package com.example.school.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.school.room.ItemConverter
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Record(
    @TypeConverters(ItemConverter::class) val records: List<Item>? = listOf(),
    val nhits: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable
