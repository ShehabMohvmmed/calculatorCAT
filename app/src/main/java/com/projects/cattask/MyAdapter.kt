package com.projects.cattask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projects.cattask.databinding.RowLayoutBinding

class MyAdapter(): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private var myList = emptyList<String>()

    class MyViewHolder(val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.itemView.findViewById<TextView>(R.id.txtLog).text = myList[position]
        holder.binding.txtLog.text = myList[position]

    }


    fun setData(newList: List<String>) {
        myList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return myList.size
    }


}