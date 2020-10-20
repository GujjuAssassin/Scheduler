package com.example.scheduler

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduler.Database.Task
import com.example.scheduler.fragments.TaskEditFragment

class RVAdapter internal constructor(private val context: Context, private val navHostController : NavController) : RecyclerView.Adapter<RVAdapter.RVAViewHolder>() {

    private val taskViewModel:TaskViewModel = ViewModelProvider(context as ViewModelStoreOwner).get(TaskViewModel::class.java)
    private var list: MutableList<Task>? = null


    init {
        taskViewModel.taskList.observe(context as LifecycleOwner, Observer{
            list = it as MutableList<Task>
            notifyDataSetChanged()
        })
    }

    class RVAViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName:TextView = itemView.findViewById(R.id.tasknameTextView)
        val taskTime:TextView = itemView.findViewById(R.id.timeTextview)
        val editButton: Button  = itemView.findViewById(R.id.edit_button)
        val deleteButton: Button  = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        RVAViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.content_main, parent, false))


    override fun onBindViewHolder(holder: RVAViewHolder, position: Int) {
        holder.taskName.text = list?.get(position)?.TaskName
        holder.taskTime.text = list?.get(position)?.Time
        holder.editButton.setOnClickListener{
            Toast.makeText(context, "Edit Task " + holder.adapterPosition, Toast.LENGTH_SHORT).show()
            val args = Bundle()
            args.putParcelable(TaskEditFragment.SelectedTask, list!![holder.adapterPosition])
            navHostController.navigate(R.id.taskEditFragment, args)
        }
        holder.deleteButton.setOnClickListener{
            Toast.makeText(context, "Deleting task " + holder.adapterPosition, Toast.LENGTH_SHORT).show()
            taskViewModel.deleteTask(list!![holder.adapterPosition])
        }
    }

    fun updateTask(task: Task){
        taskViewModel.updateTask(task)
    }

    override fun getItemCount(): Int {
        if (list == null) {
            return 0
        }
        return list!!.size
    }

    fun addItem(taskName: String){
        taskName.let {
            if(it.isNotEmpty()){
                val addTask = Task(it, "9:00 - 17:00")
                taskViewModel.insertTask(addTask)
                notifyDataSetChanged()
            }else
                Toast.makeText(context,"No task Added",Toast.LENGTH_SHORT).show()
        }
    }

}