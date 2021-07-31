package com.example.home.db

import android.nfc.Tag
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * 针对room无法识别非基础类型的，所以无法映射成数据库字段，所以需要自定义TypeConverter类，手动进行类型转换
 * 然后在Entity类还需要添加这里@TypeConverters，2.0以前是可以加到属性上的，2.0以后则会报错。只能加到实体类上
 */
class TagTypeConverter {
    @TypeConverter
    fun stringToObject(value:String):List<Tag>{
        val listType = object :TypeToken<List<Tag>>(){}.type
        return Gson().fromJson(value,listType)
    }

    @TypeConverter
    fun objectToString(list: List<Tag>):String{
        return Gson().toJson(list)
    }
}