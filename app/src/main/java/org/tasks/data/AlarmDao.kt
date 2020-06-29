package org.tasks.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlarmDao {
    @Query("SELECT alarms.* FROM alarms INNER JOIN tasks ON tasks._id = alarms.task "
            + "WHERE tasks.completed = 0 AND tasks.deleted = 0 AND tasks.lastNotified < alarms.time "
            + "ORDER BY time ASC")
    suspend fun getActiveAlarms(): List<Alarm>

    @Query("SELECT alarms.* FROM alarms INNER JOIN tasks ON tasks._id = alarms.task "
            + "WHERE tasks._id = :taskId AND tasks.completed = 0 AND tasks.deleted = 0 AND tasks.lastNotified < alarms.time "
            + "ORDER BY time ASC")
    suspend fun getActiveAlarms(taskId: Long): List<Alarm>

    @Query("SELECT * FROM alarms WHERE task = :taskId ORDER BY time ASC")
    suspend fun getAlarms(taskId: Long): List<Alarm>

    @Delete
    suspend fun delete(alarm: Alarm)

    @Insert
    suspend fun insert(alarm: Alarm): Long

    @Insert
    suspend fun insert(alarms: Iterable<Alarm>)
}