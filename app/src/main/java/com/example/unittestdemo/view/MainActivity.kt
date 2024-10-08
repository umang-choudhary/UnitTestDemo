package com.example.unittestdemo.view

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var employeeRepository: EmployeeRepository
    private lateinit var binding: ActivityMainBinding
    private lateinit var employeeViewModel: EmployeeViewModel
    private var isOnCreateExecuted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isOnCreateExecuted = true

        binding.includeTripInfo.txtViewTripTitle.text = "Trip No : "
        binding.includeTripInfo.txtViewTripValue.text = "345789"

        employeeRepository = EmployeeRepository(
            AppDatabase.getInstance(applicationContext)!!.employeeDao()
        )
        val factory = EmployeeViewModelFactory(employeeRepository)
        employeeViewModel = ViewModelProvider(this, factory)[EmployeeViewModel::class.java]

        refreshUIData()
        setListener()
    }

    override fun onResume() {
        super.onResume()

        if (isOnCreateExecuted) {
            isOnCreateExecuted = false
        } else {
            refreshUIData()
        }
    }

    private fun refreshUIData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val employeeList = employeeRepository.getAllEmployee()
            Log.d("--UnitTestDemo--", "MainActivity initData ${Thread.currentThread().name}")

            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "All data fetched successfully",
                    Toast.LENGTH_SHORT
                ).show()

                setEmployeeData(employeeList)

                Log.d("--UnitTestDemo--", "MainActivity initData end ${Thread.currentThread().name}")
            }

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