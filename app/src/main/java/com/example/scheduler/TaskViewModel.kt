package com.example.scheduler

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.scheduler.Database.Task
import com.example.scheduler.Database.TaskDatabase
import com.example.scheduler.Database.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application:Application): AndroidViewModel(application) {

    private val repo: TaskRepo

    val taskList: LiveData<List<Task>>

    init {
        val taskDao = TaskDatabase.getDatabaseInstance(application).taskDao()
        repo = TaskRepo(taskDao)
        taskList = repo.taskList
    }

    fun insertTask(task:Task) = viewModelScope.launch(Dispatchers.IO) { repo.insertTask(task) }


    fun deleteTask(task:Task) = viewModelScope.launch(Dispatchers.IO){ repo.deleteTask(task) }

    fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) { repo.updateTask(task) }

}