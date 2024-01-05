package com.jp.test.dependencyinjectionapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jp.test.dependencyinjectionapp.utils.DateConverter
import com.jp.test.dependencyinjectionapp.models.UserData
import com.jp.test.dependencyinjectionapp.models.UserExpenses

@Database(entities = [UserData::class, UserExpenses::class], version = 3)
@TypeConverters(DateConverter::class)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userExpensesDao() : UserExpensesDao

}