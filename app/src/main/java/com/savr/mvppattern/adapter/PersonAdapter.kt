package com.savr.mvppattern.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savr.mvppattern.R
import com.savr.mvppattern.model.Person
import kotlinx.android.synthetic.main.person_item.view.*

class PersonAdapter (private var datas:List<Person>, private var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            person: Person,
            onItemClickListener: OnItemClickListener
        ){
            itemView.text_idPerson.text = person.id.toString()
            itemView.text_emailPerson.text = person.email
            itemView.text_namePerson.text = person.name
            itemView.text_phonePerson.text = person.phone
            itemView.setOnClickListener {
                onItemClickListener.onItemClick(person)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position], onItemClickListener)
    }

    interface OnItemClickListener {
        fun onItemClick(person: Person)
    }
}