package com.example.service

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(info: UserInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(info: UserInfo)

    @Delete
    fun deleteUser(info: UserInfo)

    @Query("select * from tb_user where id =:id")
    fun queryLiveUser(id: Int = 0): LiveData<UserInfo>

    @Query("select * from tb_user where id =:id")
    fun queryUser(id: Int = 0): UserInfo?
}