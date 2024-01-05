package com.jp.test.dependencyinjectionapp.module

import android.content.Context
import androidx.room.Room
import com.jp.test.dependencyinjectionapp.database.UserDao
import com.jp.test.dependencyinjectionapp.database.UserDatabase
import com.jp.test.dependencyinjectionapp.database.UserExpensesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "userDatabase").fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appUserDatabase: UserDatabase) : UserDao{
        return appUserDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideUserExpensesDao(appUserDatabase: UserDatabase) : UserExpensesDao{
        return appUserDatabase.userExpensesDao()
    }
}