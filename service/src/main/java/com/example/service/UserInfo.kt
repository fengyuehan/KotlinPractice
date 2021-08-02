package com.example.service

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class UserInfo (
    val admin: Boolean,
    val coinCount: Int,
    val email: String,
    val icon: String,
    @PrimaryKey
    val id: Int,
    val nickname: String,
    val password: String,
    val publicName: String,
    val token: String,
    val type: Int,
    val username: String
)