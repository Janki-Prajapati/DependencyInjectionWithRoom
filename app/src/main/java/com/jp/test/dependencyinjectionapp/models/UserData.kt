package com.jp.test.dependencyinjectionapp.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class UserData(@PrimaryKey(autoGenerate = true) val id: Int = 0, val email : String, val password : String)
