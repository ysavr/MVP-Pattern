package com.savr.mvppattern.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.recyclerview.widget.LinearLayoutManager
import com.savr.mvppattern.R
import com.savr.mvppattern.adapter.PersonAdapter
import com.savr.mvppattern.dbhelper.DBHelper
import com.savr.mvppattern.model.Person
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    private lateinit var db:DBHelper
    private var listPerson:ArrayList<Person> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        db = DBHelper(this)

        my_recycler.layoutManager = LinearLayoutManager(this)
        my_recycler.setHasFixedSize(true)

        refreshData()

        button_add.setOnClickListener {
            val person = Person(
                Integer.parseInt(text_idPerson.text.toString()),
                text_name.text.toString(),
                text_email.text.toString(),
                text_phone.text.toString())
            db.addPerson(person)
            refreshData()
        }

        button_edit.setOnClickListener {
            val person = Person(
                Integer.parseInt(text_idPerson.text.toString()),
                text_name.text.toString(),
                text_email.text.toString(),
                text_phone.text.toString())
            db.updatePerson(person)
            refreshData()
        }

        button_delete.setOnClickListener {
            val person = Person(
                Integer.parseInt(text_idPerson.text.toString()),
                text_name.text.toString(),
                text_email.text.toString(),
                text_phone.text.toString())
            db.deletePerson(person)
            refreshData()
        }
    }

    private fun refreshData() {
        listPerson = db.allPerson as ArrayList<Person>
        my_recycler.adapter = PersonAdapter(listPerson, object : PersonAdapter.OnItemClickListener{
            override fun onItemClick(person: Person) {
                text_idPerson.text = Editable.Factory.getInstance().newEditable(person.id.toString())
                text_name.text = Editable.Factory.getInstance().newEditable(person.name)
                text_email.text = Editable.Factory.getInstance().newEditable(person.email)
                val phone = if (person.phone==null) ""
                else person.phone!!
                text_phone.text = Editable.Factory.getInstance().newEditable(phone)
            }
        })
        (my_recycler.adapter as PersonAdapter).notifyDataSetChanged()
    }
}
