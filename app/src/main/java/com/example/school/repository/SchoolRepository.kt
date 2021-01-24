package com.example.school.repository

import androidx.lifecycle.LiveData
import com.example.school.domain.Record
import com.example.school.network.SchoolApi
import com.example.school.room.SchoolRoomDatabase

class SchoolRepository(private val database: SchoolRoomDatabase) {

    val record: LiveData<Record> = database.schoolDao.getSchool()

    suspend fun refreshSchool(query: String) {
        val getSchool = SchoolApi.retrofitService.getSchoolAsync(query).await()

        database.schoolDao.deleteSchool()

        database.schoolDao.insertSchool(getSchool)

    }

}
