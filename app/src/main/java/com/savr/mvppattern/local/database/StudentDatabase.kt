package com.savr.mvppattern.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.savr.mvppattern.local.dao.StudentDao
import com.savr.mvppattern.local.entity.StudentEntity

@Database(entities = arrayOf(StudentEntity::class), version = 1)
abstract class StudentDatabase: RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        private var INSTANCE:StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase? {
            if (INSTANCE == null) {
                synchronized(StudentDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            StudentDatabase::class.java, "studentdata.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}