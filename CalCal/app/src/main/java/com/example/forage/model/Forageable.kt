package com.example.forage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forageable")
data class Forageable(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,                 // 날짜 정보를 저장하는 변수
    val morning: String,              // 아침 식단 정보를 저장하는 변수
    val lunch: String,                // 점심 식단 정보를 저장하는 변수
    val morning_kcal: Int,            // 아침 칼로리 정보를 저장하는 변수
    val lunch_kcal: Int,              // 점심 칼로리 정보를 저장하는 변수
    val dinner_kcal: Int,             // 저녁 칼로리 정보를 저장하는 변수
    val kcal_total: Int,              // 총 칼로리 정보를 저장하는 변수
    val dinner: String?               // 저녁 식단 정보를 저장하는 변수
)
