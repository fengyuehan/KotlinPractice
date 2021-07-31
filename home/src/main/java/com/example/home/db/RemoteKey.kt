package com.example.home.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab_remote_key")
class RemoteKey (@PrimaryKey
                 val articleId: Int,
                 val articleType: Int,
                 val prevKey: Int?,
                 val nextKey: Int?)