package com.savr.mvppattern.local.dao

import androidx.room.*
import com.savr.mvppattern.local.entity.StudentEntity
import io.reactivex.Flowable

@Dao
interface StudentDao {
    @Query("SELECT * from students")
    fun getAll(): Flowable<List<StudentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: StudentEntity)

    @Delete
    fun delete(student: StudentEntity)

    @Query("UPDATE students SET name =:studentName, nim =:studentNim, kelamin =:studentGen WHERE id =:studentId")
    fun update(studentId: Long, studentName:String, studentNim:String, studentGen:String)
}