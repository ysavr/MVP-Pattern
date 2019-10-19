package com.savr.mvppattern.presenter

import com.savr.mvppattern.data.ApiCall
import com.savr.mvppattern.views.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainPresenterImpl(private val view: MainView) : MainPresenter{
    override fun getData() {
        view.loading(true)
        ApiCall.instance().getData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    view.loading(false)
                    if (it.body()!!.isNotEmpty()){
                        view.success(it.body()!!)
                    } else {
                        view.error("Data tidak ada")
                    }
                },{
                    view.loading(false)
                    view.error(it.message!!)
                }
            )
    }
}