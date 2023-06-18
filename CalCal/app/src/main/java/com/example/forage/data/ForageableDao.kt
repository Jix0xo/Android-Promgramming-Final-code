package com.example.forage.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.forage.model.Forageable
import kotlinx.coroutines.flow.Flow

@Dao
interface ForageableDao {
    @Query("SELECT * FROM forageable")
    fun getForageables(): Flow<List<Forageable>>  // 모든 Forageable 데이터를 가져오는 쿼리 결과를 Flow 형태로 반환하는 메서드

    @Query("SELECT * FROM forageable WHERE id = :id")
    fun getForageable(id: Long): Flow<Forageable>  // 특정 id에 해당하는 Forageable 데이터를 가져오는 쿼리 결과를 Flow 형태로 반환하는 메서드

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(forageable: Forageable)  // Forageable 데이터를 삽입하는 메서드

    @Update
    fun update(forageable: Forageable)  // Forageable 데이터를 업데이트하는 메서드

    @Delete
    fun delete(forageable: Forageable)  // Forageable 데이터를 삭제하는 메서드
}
