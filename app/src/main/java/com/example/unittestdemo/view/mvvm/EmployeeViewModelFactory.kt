package com.example.unittestdemo.view.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EmployeeViewModelFactory(private val repository: EmployeeRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EmployeeViewModel(repository) as T
    }


}