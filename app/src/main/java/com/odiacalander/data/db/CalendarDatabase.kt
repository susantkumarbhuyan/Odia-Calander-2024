package com.odiacalander.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.odiacalander.data.db.entity.MonthDetailsEntity
import com.odiacalander.data.db.entity.MonthImgsEntity

@Database(entities = [MonthImgsEntity::class, MonthDetailsEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CalendarDatabase : RoomDatabase() {

    abstract fun calendarDao(): CalendarDao

    companion object {
        @Volatile
        private var INSTANCE: CalendarDatabase? = null
        fun getDatabase(context: Context): CalendarDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =  Room.databaseBuilder(
                        context,
                        CalendarDatabase::class.java,
                        "calendarDB"
                    ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}