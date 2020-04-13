package com.savr.mvppattern.local.entity


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "students")
data class StudentEntity (
    @ColumnInfo(name = "name")var name: String,
    @ColumnInfo(name = "nim")var nim: String,
    // MIGRATE 1->2
    @ColumnInfo(name = "kelamin")var kelamin: String,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
): Parcelable