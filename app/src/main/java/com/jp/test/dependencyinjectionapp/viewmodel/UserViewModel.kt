package com.jp.test.dependencyinjectionapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.test.dependencyinjectionapp.database.UserExpensesRepository
import com.jp.test.dependencyinjectionapp.database.UserRepository
import com.jp.test.dependencyinjectionapp.models.DateCount
import com.jp.test.dependencyinjectionapp.models.UserData
import com.jp.test.dependencyinjectionapp.models.UserExpenses
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userExpensesRepository: UserExpensesRepository
) : ViewModel() {

    fun getAllUsers(): List<UserData> {
        return userRepository.getAllUsers()
    }

    fun isUserExists(email: String) = runBlocking {
        val isExists = async {
            userRepository.isUserExists(email)
        }

        isExists.start()
        isExists.await()
    }

    fun getUser(email: String) = runBlocking {
        val userData = async {
            userRepository.getUser(email)
        }

        userData.start()
        userData.await()
    }

    fun insertUser(userData: UserData) {
        viewModelScope.launch(Dispatchers.IO) {

            userRepository.insertUser(userData)
        }
    }

    fun insertExpense(userExpenses: UserExpenses) {
        viewModelScope.launch(Dispatchers.IO) {
            userExpensesRepository.insertExpense(userExpenses = userExpenses)
        }
    }

    fun updateExpense(amountSpend: Long, description: String, userId: Int, date: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            userExpensesRepository.updateExpense(amountSpend, description, userId, date)
        }
    }

    fun getExpenses(userId: Int): LiveData<List<UserExpenses>> {
        return userExpensesRepository.getUserExpenses(userId)
    }

    fun getExpenses(userId: Int, date: Date): LiveData<List<UserExpenses>> {
        return userExpensesRepository.getUserExpenses(userId, date)
    }

    fun deleteUserExpense(userExpenses: UserExpenses) {
        viewModelScope.launch(Dispatchers.IO){
             userExpensesRepository.deleteUserExpense(userExpenses)
        }
    }

    fun updateUserExpense(userExpenses: UserExpenses){
        viewModelScope.launch(Dispatchers.IO){
            userExpensesRepository.updateUserExpense(userExpenses)
        }
    }
    fun getExpenseGroupedByDate(userId: Int) : LiveData<List<DateCount>>{
        return userExpensesRepository.getExpenseGroupedByDate(userId)
    }

}