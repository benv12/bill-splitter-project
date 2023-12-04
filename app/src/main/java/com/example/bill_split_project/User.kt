package com.example.bill_split_project

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(

    @PrimaryKey(autoGenerate = true)val id: Int?,
    @ColumnInfo(name = "user_name")val userName: String?,
    @ColumnInfo(name = "pass_word")val passWord: String?,

)
