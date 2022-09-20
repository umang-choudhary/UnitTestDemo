package com.example.unittestdemo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.unittestdemo.db.entity.Employee

@Dao
interface EmployeeDao {

    @Insert
    suspend fun addEmployee(employee: Employee): Long

    @Query("Select * from EmployeeRecords ORDER BY joiningDate")
    suspend fun getAllEmployee(): List<Employee>

}