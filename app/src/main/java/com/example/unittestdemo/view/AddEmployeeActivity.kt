package com.example.unittestdemo.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.unittestdemo.databinding.ActivityAddEmployeeBinding
import com.example.unittestdemo.db.AppDatabase
import com.example.unittestdemo.db.entity.Employee
import com.example.unittestdemo.utils.validateEmail
import com.example.unittestdemo.utils.validatePinCode
import com.example.unittestdemo.utils.validateUserName
import com.example.unittestdemo.view.mvvm.EmployeeRepository
import com.example.unittestdemo.view.mvvm.EmployeeViewModel
import com.example.unittestdemo.view.mvvm.EmployeeViewModelFactory
import java.util.*

class AddEmployeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEmployeeBinding
    private lateinit var employeeViewModel: EmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initData()
        setListener()
    }

    private fun initData() {
        binding.textViewSuccessMsg.visibility = View.GONE

        val groceryRepository = EmployeeRepository(
            AppDatabase.getInstance(applicationContext)!!.employeeDao()
        )
        val factory = EmployeeViewModelFactory(groceryRepository)
        employeeViewModel = ViewModelProvider(this, factory)[EmployeeViewModel::class.java]
    }

    private fun setListener() {
        binding.btnSave.setOnClickListener {
            val name = binding.editTextUserName.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val pinCode = binding.editTextPinCode.text.toString().trim()

            when {
                validateUserName(name) != null -> {
                    val errorId =
                        validateUserName(name)!!.validationMessage
                    Toast.makeText(this@AddEmployeeActivity, errorId, Toast.LENGTH_SHORT).show()
                }
                validateEmail(email) != null -> {
                    val errorId = validateEmail(email)!!.validationMessage
                    Toast.makeText(this@AddEmployeeActivity, errorId, Toast.LENGTH_SHORT).show()
                }
                validatePinCode(pinCode) != null -> {
                    val errorId = validatePinCode(pinCode)!!.validationMessage
                    Toast.makeText(this@AddEmployeeActivity, errorId, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val employee = Employee(name, email, pinCode, Date(), "")
                    employeeViewModel.insertEmployee(employee).observe(this) {
                        val msg = it.message
                        Toast.makeText(this@AddEmployeeActivity, msg, Toast.LENGTH_SHORT).show()
                        binding.editTextUserName.text.clear()
                        binding.editTextEmail.text.clear()
                        binding.editTextPinCode.text.clear()
                        binding.textViewSuccessMsg.visibility = View.VISIBLE
                        binding.textViewSuccessMsg.text = msg

                        finish()
                    }
                }
            }
        }

    }

}