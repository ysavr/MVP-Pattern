package com.savr.mvppattern.views.student

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.savr.mvppattern.R
import com.savr.mvppattern.local.database.StudentDatabase
import com.savr.mvppattern.local.entity.StudentEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_detail_student.*
import kotlinx.android.synthetic.main.content_detail_student.*
import kotlinx.android.synthetic.main.input_dialog.view.*

class DetailStudentActivity : AppCompatActivity() {

    lateinit var studentEntity: StudentEntity
    private var studentDatabase: StudentDatabase? = null
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_student)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        studentEntity = intent.getParcelableExtra("student_object")

        textViewName.text = "Nama = ${studentEntity.name}"
        textViewNim.text = "Nim = ${studentEntity.nim}"
        textViewGender.text = "Jenis Kelamin = ${studentEntity.gender}"

        studentDatabase = StudentDatabase.getInstance(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete -> {
                deleteStudent(studentEntity)
                Toast.makeText(this, "Data ${studentEntity.name} berhasil dihapus", Toast.LENGTH_LONG).show()
                finish()
            }
            R.id.edit -> {
                val dialogBuilder = AlertDialog.Builder(this)
                val view = layoutInflater.inflate(R.layout.input_dialog, null)
                dialogBuilder.setView(view)
                dialogBuilder.setTitle("Masukkan data baru")
                val etName = view.ed_student_name
                val etNim = view.ed_student_id
                val radioGroupGender = view.radio_group_gender

                dialogBuilder.setPositiveButton("Update"){ _: DialogInterface, _: Int ->
                    val studentName = etName.text
                    val studentNim = etNim.text
                    val gender: String
                    when (radioGroupGender.checkedRadioButtonId) {
                        R.id.radio_female -> gender = "Perempuan"
                        else -> gender = "Laki-laki"
                    }
                    updateStudent(studentEntity.id, studentName.toString(), studentNim.toString(), gender)
                    Toast.makeText(this, "Data berhasil diubah $studentName ", Toast.LENGTH_LONG).show()
                    textViewName.text = "Nama = $studentName"
                    textViewNim.text = "Nim = $studentNim"
                    textViewGender.text = "Jenis Kelamin = $gender"
                }
                dialogBuilder.setNegativeButton("Batal") { _: DialogInterface, _: Int ->
                }
                dialogBuilder.show()
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
    }

    private fun updateStudent(id: Long, studentName: String, studentNim: String, gender: String) {
        compositeDisposable.add(Observable.fromCallable {
            studentDatabase?.studentDao()?.update(id, studentName, studentNim, gender) }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    private fun deleteStudent(studentEntity: StudentEntity) {
        compositeDisposable.add(Observable.fromCallable{studentDatabase?.studentDao()?.delete(studentEntity)}
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
    }

    override fun onDestroy() {
        super.onDestroy()
        StudentDatabase.destroyInstance()
        compositeDisposable.dispose()
    }
}
