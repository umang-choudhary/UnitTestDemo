package com.example.unittestdemo.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.unittestdemo.databinding.EmployeeListItemBinding
import com.example.unittestdemo.db.entity.Employee
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EmployeeListAdapter(
    private val context: Activity,
    private val list: ArrayList<Employee>,
) : androidx.recyclerview.widget.RecyclerView.Adapter<EmployeeListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: EmployeeListItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            EmployeeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textViewName.text = list[position].name
        holder.binding.textViewEmail.text = list[position].email
        holder.binding.textViewPinCode.text = list[position].pinCode

        val format = SimpleDateFormat("dd/MM/yyy", Locale.US)
        val dateString = format.format(list[position].joiningDate)
        holder.binding.textViewJoiningDate.text = dateString
    }

    override fun getItemCount(): Int {
        return list.size
    }

}