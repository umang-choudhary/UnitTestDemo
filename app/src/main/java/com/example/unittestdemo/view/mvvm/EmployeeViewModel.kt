package com.example.unittestdemo.view.mvvm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unittestdemo.db.entity.Employee
import com.example.unittestdemo.model.EventMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeViewModel(private val employeeRepository: EmployeeRepository) : ViewModel() {

    private val allEmployeeData = MutableLiveData<List<Employee>>()

    fun insertEmployee(employee: Employee): MutableLiveData<EventMsg> {
        val insertStatusMessage = MutableLiveData<EventMsg>()

        viewModelScope.launch(Dispatchers.IO) {
            val newRowId: Long = employeeRepository.insertEmployee(employee)
            Log.d(
                "--UnitTestDemo--",
                "EmployeeViewModel insertEmployee ${Thread.currentThread().name}"
            )

            withContext(Dispatchers.Main) {
                if (newRowId > -1) {
                    insertStatusMessage.value = EventMsg("Employee Added Successfully")
                } else {
                    insertStatusMessage.value = EventMsg("Error Occurred")
                }
                Log.d(
                    "--UnitTestDemo--",
                    "EmployeeViewModel insertEmployee end ${Thread.currentThread().name}"
                )
            }
        }
        return insertStatusMessage
    }

    fun getAllEmployee(): MutableLiveData<List<Employee>> {
        viewModelScope.launch(Dispatchers.IO) {
            val employeeList: List<Employee> = employeeRepository.getAllEmployee()
            Log.d(
                "--UnitTestDemo--",
                "EmployeeViewModel getAllEmployee ${Thread.currentThread().name}"
            )

            withContext(Dispatchers.Main) {
                allEmployeeData.value = employeeList
                Log.d(
                    "--UnitTestDemo--",
                    "EmployeeViewModel getAllEmployee end ${Thread.currentThread().name}"
                )
            }
        }
        return allEmployeeData
    }

}