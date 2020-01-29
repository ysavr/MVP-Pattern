package com.savr.mvppattern.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savr.mvppattern.R
import com.savr.mvppattern.model.ResponseKodePos
import kotlinx.android.synthetic.main.list_item.view.*

class KodePosAdapter (var datas:List<ResponseKodePos>) : RecyclerView.Adapter<KodePosAdapter.ViewHolder>() {

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(responseKodePos: ResponseKodePos) {
            itemView.text_name.text = responseKodePos.kecamatan
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return datas.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }
}