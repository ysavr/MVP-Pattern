package com.savr.mvppattern.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.savr.mvppattern.local.dao.StudentDao
import com.savr.mvppattern.local.entity.StudentEntity

@Database(entities = arrayOf(StudentEntity::class), version = 2, exportSchema = false)
abstract class StudentDatabase: RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        private var INSTANCE:StudentDatabase? = null

        fun getInstance(context: Context): StudentDatabase? {

            val MIGRATION_1_2 = object : Migration(1,2) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL(
                        "CREATE TABLE users_new (id INTEGER NOT NULL , " +
                                "name TEXT NOT NULL, nim TEXT NOT NULL, kelamin TEXT NOT NULL, PRIMARY KEY(id))")
                    // Copy the data
                    database.execSQL(
                        "INSERT INTO users_new (id, name, nim, kelamin) " +
                                "SELECT id, name, nim, gender FROM students")

                    database.execSQL("DROP TABLE students")
                    database.execSQL("ALTER TABLE users_new RENAME TO students")

                }
            }

            if (INSTANCE == null) {
                synchronized(StudentDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            StudentDatabase::class.java, "studentdata.db")
                        .addMigrations(MIGRATION_1_2)
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