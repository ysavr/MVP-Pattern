package com.savr.mvppattern.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savr.mvppattern.R
import com.savr.mvppattern.local.entity.StudentEntity
import kotlinx.android.synthetic.main.item_student.view.*

class StudentAdapter(val studentList : ArrayList<StudentEntity>, val context : Context)
    : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(studentEntity: StudentEntity) {
            itemView.text_view_student_gen.text = studentEntity.gender
            itemView.text_view_student_name.text = studentEntity.name
            itemView.text_view_student_nim.text = studentEntity.nim

            itemView.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(studentList[position])
    }
}