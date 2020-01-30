package com.savr.mvppattern.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.savr.mvppattern.R
import com.savr.mvppattern.databinding.ActivitySecondBinding
import com.savr.mvppattern.model.User

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivitySecondBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_second)

        binding.user = User("safrudin", "yusuf")
    }
}
