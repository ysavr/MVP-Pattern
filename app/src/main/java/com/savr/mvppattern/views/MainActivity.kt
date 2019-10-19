package com.savr.mvppattern.views

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.savr.mvppattern.R
import com.savr.mvppattern.model.ResponseKodePos
import com.savr.mvppattern.presenter.MainPresenter
import com.savr.mvppattern.presenter.MainPresenterImpl

class MainActivity : AppCompatActivity(), MainView {
    private var presenter = MainPresenterImpl(this)
    private var progressDialog:ProgressDialog? = null

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
        Toast.makeText(applicationContext, "Jumlah Data: ${data.size}", Toast.LENGTH_LONG).show()
    }

    override fun error(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.getData()
    }
}
