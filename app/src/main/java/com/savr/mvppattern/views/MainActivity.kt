package com.savr.mvppattern.views

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.savr.mvppattern.R
import com.savr.mvppattern.adapter.KodePosAdapter
import com.savr.mvppattern.model.ResponseKodePos
import com.savr.mvppattern.presenter.MainPresenter
import com.savr.mvppattern.presenter.MainPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Math.random
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

class MainActivity : AppCompatActivity(), MainView {
    private var presenter = MainPresenterImpl(this)
    private var progressDialog:ProgressDialog? = null
    var kodePosList: ArrayList<ResponseKodePos> = ArrayList()
    private val TAG = MainActivity::class.qualifiedName

    override fun loading(isLoading: Boolean) {
        if (isLoading){
            progressDialog = ProgressDialog(this)
            progressDialog?.setMessage("Get Data")
            progressDialog?.show()
        }else{
            progressDialog?.dismiss()
        }
    }

    override fun success(data: List<ResponseKodePos>) {
        kodePosList = data as ArrayList<ResponseKodePos>
        recycler.adapter = KodePosAdapter(kodePosList)
        (recycler.adapter as KodePosAdapter).notifyDataSetChanged()
        Toast.makeText(applicationContext, "Jumlah Data: ${data.size}", Toast.LENGTH_LONG).show()
    }

    override fun error(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.setHasFixedSize(true)

        presenter.getData()

        buttonNext.setOnClickListener {
            startActivity(Intent(applicationContext,SecondActivity::class.java))
        }
    }
}
