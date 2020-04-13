package com.savr.mvppattern.views.student

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.savr.mvppattern.R
import com.savr.mvppattern.adapter.StudentAdapter
import com.savr.mvppattern.local.database.StudentDatabase
import com.savr.mvppattern.local.entity.StudentEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.android.synthetic.main.input_dialog.view.*

class StudentActivity : AppCompatActivity() {

    private val students: ArrayList<StudentEntity> = ArrayList()
    private var studentDatabase: StudentDatabase? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        student_recycler.layoutManager = LinearLayoutManager(applicationContext)
        student_recycler.adapter = StudentAdapter(students, this)
        studentDatabase = StudentDatabase.getInstance(this)
        getAllData()

        fab_add_student.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.input_dialog, null)
            dialogBuilder.setView(view)
            dialogBuilder.setTitle("Masukkan data baru")
            val etName = view.ed_student_name
            val etNim = view.ed_student_id
            val radioGroupGender = view.radio_group_gender

            dialogBuilder.setPositiveButton("Tambahkan"){ _:DialogInterface, _: Int ->
                val studentName = etName.text
                val studentNim = etNim.text
                var gender: String
                val selectedRadioButton = radioGroupGender.checkedRadioButtonId
                Log.v("test", "" + selectedRadioButton)
                when (selectedRadioButton) {
                    R.id.radio_female -> gender = "Perempuan"
                    else -> gender = "Laki-laki"
                }
                insertToDb(StudentEntity(studentName.toString(), studentNim.toString(), gender))
                applicationContext.toast("Data berhasil dimasukkan")
            }
            dialogBuilder.setNegativeButton("Batal") { _: DialogInterface, _: Int ->
            }
            dialogBuilder.show()
        }
    }

    private fun Context.toast(message:CharSequence){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun insertToDb(student:StudentEntity){
        compositeDisposable.add(Observable.fromCallable{studentDatabase?.studentDao()?.insert(student)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    private fun getAllData() {
       compositeDisposable.add(studentDatabase!!.studentDao().getAll()
           .subscribeOn(Schedulers.computation())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe {
               students.clear()
               students.addAll(it)
               student_recycler.adapter = StudentAdapter(students, this)
           })
    }

    override fun onDestroy() {
        super.onDestroy()
        StudentDatabase.destroyInstance()
        compositeDisposable.dispose()
    }
}

