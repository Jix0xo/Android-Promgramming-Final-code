package com.example.forage.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.forage.model.Forageable

@Database(entities = [Forageable::class], version = 1, exportSchema = false)
abstract class ForageDatabase : RoomDatabase() {
    abstract fun forageableDao(): ForageableDao

    companion object {
        @Volatile
        private var INSTANCE: ForageDatabase? = null

        // RoomDatabase의 인스턴스를 생성하는 메서드
        fun getDatabase(context: Context) = INSTANCE ?: synchronized(this) {
            // 인스턴스가 null일 경우에만 동기화 블록 진입
            Room.databaseBuilder(
                context.applicationContext,
                ForageDatabase::class.java,
                "forageable_database"
            ).build().apply {
                // 생성한 인스턴스를 변수에 할당하고 반환
                INSTANCE = this
            }
        }
    }
}
