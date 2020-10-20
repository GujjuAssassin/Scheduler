package com.example.scheduler.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao  {

    @Query("select * from TasksTable")
    fun getAllTasks(): LiveData<List<Task>>

    @Insert
    suspend fun insertTask(task:Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task:Task)

}