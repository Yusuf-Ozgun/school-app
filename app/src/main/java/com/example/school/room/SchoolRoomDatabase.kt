package com.example.school.room

import android.content.Context
import androidx.room.*
import com.example.school.domain.Record

@Database(entities = [Record::class], version = 11, exportSchema = false)
@TypeConverters(ItemConverter::class)
abstract class SchoolRoomDatabase : RoomDatabase() {
    abstract val schoolDao: SchoolDao

    companion object {

        @Volatile
        private var INSTANCE: SchoolRoomDatabase? = null

        fun getDatabase(context: Context): SchoolRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SchoolRoomDatabase::class.java,
                        "school_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}