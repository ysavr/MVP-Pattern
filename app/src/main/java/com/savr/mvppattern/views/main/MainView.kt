package com.savr.mvppattern.views.main

import com.savr.mvppattern.model.ResponseKodePos

interface MainView {
    fun loading(isLoading: Boolean)
    fun success(data:List<ResponseKodePos>)
    fun error(message:String)
}