package com.example.unittestdemo.db

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.unittestdemo.db.dao.EmployeeDao
import com.example.unittestdemo.db.entity.Employee

@Database(
    entities = [Employee::class],
    version = 4,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2), AutoMigration(
        from = 2,
        to = 3
    ), AutoMigration(from = 3, to = 4)]
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        context.getExternalFilesDir(null)!!.absolutePath + "/UnitTestDemo/" + "unit_test_demo.db"
                    ).addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    }).build()
                }
            }
            return INSTANCE
        }

        @JvmStatic
        fun destroyInstance() {
            if (INSTANCE?.isOpen!!) {
                INSTANCE?.close()
                INSTANCE = null
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE EmployeeRecords ADD COLUMN EmployeeAddress TEXT NOT NULL DEFAULT ''")
            }
        }

    }

}