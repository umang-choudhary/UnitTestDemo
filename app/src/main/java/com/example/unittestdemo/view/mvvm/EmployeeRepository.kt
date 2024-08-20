package com.example.unittestdemo.view.mvvm

import com.example.unittestdemo.db.dao.EmployeeDao
import com.example.unittestdemo.db.entity.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class EmployeeRepository(private val employeeDao: EmployeeDao) {

    suspend fun insertEmployee(employee: Employee) = withContext(Dispatchers.IO) {
        employeeDao.addEmployee(employee)
    }

    suspend fun getAllEmployee(): List<Employee> = withContext(Dispatchers.IO) {
        delay(2000)
        employeeDao.getAllEmployee()
    }
}