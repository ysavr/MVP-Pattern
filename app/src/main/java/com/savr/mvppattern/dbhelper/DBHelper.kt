package com.savr.mvppattern.dbhelper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.savr.mvppattern.model.Person

class DBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        var DATABASE_NAME = "user_database"
        const val DATABASE_VERSION = 2
        const val TABLE_USER = "users"
        const val COL_USER_ID = "id"
        const val COL_USER_NAME = "name"
        const val COL_USER_EMAIL = "email"
        const val COL_USER_PHONE = "phone"

        const val CREATE_TABLE_USER = ("CREATE TABLE $TABLE_USER " +
                "($COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_USER_NAME TEXT, $COL_USER_EMAIL TEXT)")

        const val DATABASE_ALTER_USER_COL_USER_PHONE = ("ALTER TABLE "
                + TABLE_USER) + " ADD COLUMN " + COL_USER_PHONE + " string;"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //execute when migrating
        if (oldVersion == 1) {
            db!!.execSQL(DATABASE_ALTER_USER_COL_USER_PHONE)
        }
    }

    val allPerson:List<Person>
    @SuppressLint("Recycle")
    get() {
        val personList = ArrayList<Person>()
        val selectQuery = "SELECT * FROM $TABLE_USER"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val person = Person()
                person.id = cursor.getInt(cursor.getColumnIndex(COL_USER_ID))
                person.name = cursor.getString(cursor.getColumnIndex(COL_USER_NAME))
                person.email= cursor.getString(cursor.getColumnIndex(COL_USER_EMAIL))
                person.phone = cursor.getString(cursor.getColumnIndex(COL_USER_PHONE))
                personList.add(person)
            } while (cursor.moveToNext())
        }
        db.close()
        return  personList
    }

    fun addPerson(person: Person) {
        val db = this.readableDatabase
        val values = ContentValues()
        values.put(COL_USER_ID, person.id)
        values.put(COL_USER_NAME, person.name)
        values.put(COL_USER_EMAIL, person.email)
        values.put(COL_USER_PHONE, person.phone)

        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun updatePerson(person: Person) {
        val db = this.readableDatabase
        val values = ContentValues()
        values.put(COL_USER_ID, person.id)
        values.put(COL_USER_NAME, person.name)
        values.put(COL_USER_EMAIL, person.email)
        values.put(COL_USER_PHONE, person.phone)

        db.update(TABLE_USER, values, "$COL_USER_ID=?", arrayOf(person.id.toString()))
        db.close()
    }

    fun deletePerson(person: Person) {
        val db = this.readableDatabase

        db.delete(TABLE_USER, "$COL_USER_ID=?", arrayOf(person.id.toString()))
        db.close()
    }
}