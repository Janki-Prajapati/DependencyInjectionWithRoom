package com.jp.test.dependencyinjectionapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jp.test.dependencyinjectionapp.models.UserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<UserData>

    @Query("SELECT EXISTS(SELECT * FROM users WHERE email = :inputEmail)")
    suspend fun isUserExists(inputEmail : String): Boolean

    @Query("SELECT * FROM users WHERE email = :inputEmail")
    suspend fun getUser(inputEmail : String): UserData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(userData: UserData)
}