package com.example.unittestdemo.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "EmployeeRecords")
data class Employee(
    @ColumnInfo(name = "EmployeeName")
    val name: String,
    val email: String,
    @ColumnInfo(name = "PinCode")
    val pinCode: String,
    val joiningDate: Date,
    @ColumnInfo(name = "EmployeeAddress", defaultValue = "")
    val address: String,
    @ColumnInfo(defaultValue = "")
    val phone: String = "",
    @ColumnInfo(defaultValue = "")
    val city: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
