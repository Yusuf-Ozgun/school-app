package com.example.school.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.school.domain.Record

/*
* Interface defining all methods needed for clients usage when app is offline
*/

@Dao
interface SchoolDao {

    @Query(value = "select * from record")
    fun getSchool(): LiveData<Record>

    @Insert
    fun insertSchool(vararg record: Record)

    @Query(value = "delete from record")
    fun deleteSchool()

}