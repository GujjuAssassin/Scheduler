package com.example.scheduler.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities= arrayOf(Task::class), version = 1)
abstract class TaskDatabase : RoomDatabase() {
    
    abstract fun taskDao(): TaskDao

    companion object {

        private var DBInstance: TaskDatabase? = null

        fun getDatabaseInstance(context: Context) : TaskDatabase{
            val tempInstance = DBInstance
            if(tempInstance != null)
                return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(context, TaskDatabase::class.java, "TaskDatabase").build()
                DBInstance = instance
                return instance
            }
        }

    }

}