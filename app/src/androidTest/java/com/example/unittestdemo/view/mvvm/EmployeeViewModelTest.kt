package com.example.unittestdemo.view.mvvm

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unittestdemo.db.AppDatabase
import com.example.unittestdemo.db.entity.Employee
import com.example.unittestdemo.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class EmployeeViewModelTest {
    private lateinit var viewModel: EmployeeViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appDatabase =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        val employeeRepository = EmployeeRepository(appDatabase.employeeDao())
        viewModel = EmployeeViewModel(employeeRepository)
    }

    @Test
    fun testEmployeeViewModel() {
        val date = Date()
        val employee = Employee("Vayu", "vayu@gmail.com", "201003", date, "")
        viewModel.insertEmployee(employee)
        viewModel.getAllEmployee()

        /*viewModel.getAllEmployeeData()
            .observeForever {
                val result = it
                assertThat(result.contains(employee)).isTrue()
            }*/

        val result = getOrAwaitValue(viewModel.getAllEmployee())
        assertThat(result.contains(employee)).isTrue()

    }
}