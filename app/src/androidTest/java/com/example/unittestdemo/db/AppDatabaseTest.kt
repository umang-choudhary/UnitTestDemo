package com.example.unittestdemo.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unittestdemo.db.dao.EmployeeDao
import com.example.unittestdemo.db.entity.Employee
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var employeeDao: EmployeeDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        employeeDao = appDatabase.employeeDao()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun insertAndGetEmployee() = runBlocking {
        val date = Date()
        val employee = Employee("Umang", "umang@gmail.com", "201003", date, "")
        employeeDao.addEmployee(employee)
        val employeeList = employeeDao.getAllEmployee()
        assertThat(employeeList.contains(employee)).isTrue()
    }

}