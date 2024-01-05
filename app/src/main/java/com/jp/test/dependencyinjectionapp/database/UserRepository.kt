package com.jp.test.dependencyinjectionapp.database

import com.jp.test.dependencyinjectionapp.models.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class UserRepository @Inject constructor(private val userDao: UserDao) {

    fun getAllUsers() : List<UserData>{
        return userDao.getAllUsers()
    }

    suspend fun isUserExists(email : String) : Boolean{
        return userDao.isUserExists(email)
    }

    suspend fun getUser(email : String) : UserData{
        return userDao.getUser(email)
    }

    suspend fun insertUser(userData: UserData){
        userDao.insertUser(userData)
    }
}
