package com.jp.test.dependencyinjectionapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date


@Entity(tableName = "userExpenses")
@Parcelize
data class UserExpenses(
    @PrimaryKey(autoGenerate = true) val addressId: Int = 0,
    var amountSpend: Int?,
    var description: String?,
    val date: Date?,
    val userId: Int?
) : Parcelable
