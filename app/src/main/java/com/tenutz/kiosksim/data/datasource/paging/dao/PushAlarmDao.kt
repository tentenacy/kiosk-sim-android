package com.tenutz.kiosksim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.kiosksim.data.datasource.paging.entity.PushAlarms

@Dao
interface PushAlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pushAlarms: List<PushAlarms.PushAlarm>)

    @Query("SELECT * FROM push_alarm ORDER BY seq")
    fun selectAll(): PagingSource<Int, PushAlarms.PushAlarm>

    @Query("DELETE FROM push_alarm")
    fun clear()
}