package com.example.unittestdemo.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.unittestdemo.adapter.EmployeeListAdapter
import com.example.unittestdemo.databinding.ActivityMainBinding
import com.example.unittestdemo.db.AppDatabase
import com.example.unittestdemo.db.entity.Employee
import com.example.unittestdemo.view.mvvm.EmployeeRepository
import com.example.unittestdemo.view.mvvm.EmployeeViewModel
import com.example.unittestdemo.view.mvvm.EmployeeViewModelFactory
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initData()
        setListener()
    }

    private fun initData() {
        val groceryRepository = EmployeeRepository(
            AppDatabase.getInstance(applicationContext)!!.employeeDao()
        )
        val factory = EmployeeViewModelFactory(groceryRepository)
        employeeViewModel = ViewModelProvider(this, factory)[EmployeeViewModel::class.java]

        if (employeeViewModel.allEmployeeData.value == null) {
            lifecycleScope.launch {
                val employeeList = employeeViewModel.getAllEmployeeList()
                Toast.makeText(
                    this@MainActivity,
                    "All data fetched successfully",
                    Toast.LENGTH_SHORT
                )
                    .show()
                setEmployeeData(employeeList)
            }
            Toast.makeText(
                this@MainActivity,
                "Data loading...",
                Toast.LENGTH_SHORT
            )
                .show()
            /*employeeViewModel.getAllEmployee()
                .observe(this) {
                    val employeeList = it
                    Toast.makeText(
                        this@MainActivity,
                        "All data fetched successfully",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    setEmployeeData(employeeList)
                }*/
        } else {
            setEmployeeData(employeeViewModel.allEmployeeData.value!!)
        }

    }

    private fun setEmployeeData(employeeList: List<Employee>) {
        val employeeListAdapter =
            EmployeeListAdapter(this@MainActivity, employeeList as ArrayList<Employee>)
        val linearLayoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(this@MainActivity)
        binding.recyclerViewAllEmployee.layoutManager = linearLayoutManager

        binding.recyclerViewAllEmployee.adapter = employeeListAdapter
    }

    private fun setListener() {
        binding.btnAddNewEmployee.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEmployeeActivity::class.java)
            startActivity(intent)
        }
    }

}