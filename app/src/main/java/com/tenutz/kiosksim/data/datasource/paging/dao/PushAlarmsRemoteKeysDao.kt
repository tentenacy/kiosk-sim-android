package com.tenutz.kiosksim.data.datasource.paging.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tenutz.kiosksim.data.datasource.paging.entity.PushAlarms

@Dao
interface PushAlarmsRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(keys: List<PushAlarms.PushAlarmRemoteKeys>)

    @Query("SELECT * FROM push_alarm_remote_keys ORDER BY id ASC")
    fun selectAll(): PagingSource<Int, PushAlarms.PushAlarmRemoteKeys>

    @Query("DELETE FROM push_alarm_remote_keys")
    fun clear()
}