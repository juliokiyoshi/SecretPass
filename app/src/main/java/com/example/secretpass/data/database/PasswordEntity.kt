package com.example.secretpass.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords_table")
data class PasswordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val site: String,
    val password: String,
)


