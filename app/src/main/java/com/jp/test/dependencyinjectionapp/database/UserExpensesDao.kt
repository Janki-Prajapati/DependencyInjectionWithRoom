package com.jp.test.dependencyinjectionapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.jp.test.dependencyinjectionapp.models.DateCount
import com.jp.test.dependencyinjectionapp.models.UserExpenses
import java.util.Date

@Dao
interface UserExpensesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExpenses(expenses: UserExpenses)

    @Query("UPDATE userExpenses SET amountSpend = :amountSpend, description = :description, date = :date WHERE userId = :userId")
    suspend fun updateData(amountSpend: Long, description: String, date: Date, userId: Int)

    @Query("SELECT * FROM userExpenses WHERE userId = :userId")
    fun getExpenses(userId : Int) : LiveData<List<UserExpenses>>

    @Query("SELECT * FROM userExpenses WHERE userId = :userId AND date = :date")
    fun getExpenses(userId : Int, date: Date) : LiveData<List<UserExpenses>>

    @Delete
    suspend fun deleteUserExpense(userExpenses: UserExpenses)

    @Update
    suspend fun updateUserExpense(userExpenses: UserExpenses)

    @Query("SELECT date AS date FROM userExpenses WHERE userId = :userId GROUP BY date")
    fun getExpenseGroupedByDate(userId : Int) : LiveData<List<DateCount>>


}