package com.example.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserInfo::class],version = 1,exportSchema = false)
abstract class UserDB:RoomDatabase() {
    abstract val userDao:UserDao

    companion object{
        const val DB_NAME = "tb_user"

        @Volatile
        private var INSTANCE:UserDB? = null

        fun  get(context: Context):UserDB{
            return INSTANCE?: Room.databaseBuilder(context,UserDB::class.java, DB_NAME).build().also {
                INSTANCE = it
            }
        }
    }
}