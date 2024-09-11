package com.example.unittestdemo.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.unittestdemo.adapter.EmployeeListAdapter
import com.example.unittestdemo.databinding.ActivityAlertBinding
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
        setContentView(binding.root)

        binding.includeTripInfo.txtViewTripTitle.text = "Trip No : "
        binding.includeTripInfo.txtViewTripValue.text = "345789"

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
            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        val dialogBinding = ActivityAlertBinding.inflate(layoutInflater);

        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(false)
        if (!isFinishing) {
            dialog.show()
        }

        dialogBinding.headingTxt.text = "Confirmation"
        dialogBinding.resultTxt.text = "Are you sure want to add employee ?"

        dialogBinding.okBtn.text = "YES"
        dialogBinding.cancelBtn.text = "NO"


        dialogBinding.okBtn.setOnClickListener {
            dialog.dismiss()

            val intent = Intent(this@MainActivity, AddEmployeeActivity::class.java)
            startActivity(intent)
        }

        dialogBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

    }

}