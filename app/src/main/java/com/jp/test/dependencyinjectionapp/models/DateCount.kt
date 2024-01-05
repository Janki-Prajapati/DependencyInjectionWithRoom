package com.jp.test.dependencyinjectionapp.models

import androidx.room.Relation
import java.util.Date

data class DateCount(
    val date: Date,
    @Relation(
        entity = UserExpenses::class,
        parentColumn = "date",
        entityColumn = "date",
    ) var expenseList: List<UserExpenses>
)
