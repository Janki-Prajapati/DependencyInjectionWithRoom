package com.jp.test.dependencyinjectionapp.database

import androidx.lifecycle.LiveData
import com.jp.test.dependencyinjectionapp.models.DateCount
import com.jp.test.dependencyinjectionapp.models.UserExpenses
import java.util.Date
import javax.inject.Inject

class UserExpensesRepository @Inject constructor(private val userExpensesDao: UserExpensesDao) {

    suspend fun insertExpense(userExpenses: UserExpenses) {
        userExpensesDao.insertExpenses(userExpenses)
    }

    fun getUserExpenses(userId: Int): LiveData<List<UserExpenses>> {
        return userExpensesDao.getExpenses(userId)
    }

    fun getUserExpenses(userId: Int, date: Date): LiveData<List<UserExpenses>> {
        return userExpensesDao.getExpenses(userId, date)
    }

    suspend fun deleteUserExpense(userExpenses: UserExpenses) {
         userExpensesDao.deleteUserExpense(userExpenses)
    }

    suspend fun updateUserExpense(userExpenses: UserExpenses){
        userExpensesDao.updateUserExpense(userExpenses)
    }

    fun getExpenseGroupedByDate(userId: Int): LiveData<List<DateCount>> {
        return userExpensesDao.getExpenseGroupedByDate(userId)
    }

    suspend fun updateExpense(amountSpend: Long, description: String, userId: Int, date: Date) {
        userExpensesDao.updateData(
            amountSpend = amountSpend,
            description = description,
            userId = userId,
            date = date
        )
    }
}