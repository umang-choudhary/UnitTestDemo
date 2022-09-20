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

    val allEmployeeData = MutableLiveData<List<Employee>>()

    fun insertEmployee(employee: Employee): MutableLiveData<EventMsg> {
        val insertStatusMessage = MutableLiveData<EventMsg>()

        viewModelScope.launch {
            val newRowId: Long = employeeRepository.insertEmployee(employee)
            Log.d("---Insert Employee on---", Thread.currentThread().name)

            if (newRowId > -1) {
                insertStatusMessage.value = EventMsg("Employee Added Successfully")
            } else {
                insertStatusMessage.value = EventMsg("Error Occurred")
            }
        }
        return insertStatusMessage
    }

    fun getAllEmployee(): MutableLiveData<List<Employee>> {
        viewModelScope.launch {
            val employeeList: List<Employee> = employeeRepository.getAllEmployee()
            Log.d("---Get All Employee on---", Thread.currentThread().name)

            allEmployeeData.value = employeeList
        }
        return allEmployeeData
    }
}