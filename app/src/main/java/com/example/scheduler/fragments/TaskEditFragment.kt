package com.example.scheduler.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scheduler.Database.Task
import com.example.scheduler.MainActivity
import com.example.scheduler.R
import kotlinx.android.synthetic.main.activity_main.*


class TaskEditFragment : Fragment() {

    private var selectedTask: Task? = null
    private var keyListener: View.OnKeyListener? = null

    private lateinit var editTaskName: EditText
    private lateinit var editTime: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedTask = arguments?.getParcelable(SelectedTask)

        keyListener = object : View.OnKeyListener {
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {
                if (p2?.keyCode == KEYCODE_ENTER) {
                    val tempTask =
                        Task(editTaskName.text.toString().trim(), editTime.text.toString().trim())
                    tempTask.id = selectedTask!!.id
                    if (tempTask != selectedTask) {
                        val args = Bundle()
                        args.putParcelable(EditedTask, tempTask)
                        hideKeyboard(p0)
                        MainActivity.logit("Not Same Updating")
                        findNavController().navigate(R.id.FirstFragment, args)
                        return true
                    }
                    MainActivity.logit("Both are same")
                    hideKeyboard(p0)
                    findNavController().navigate(R.id.FirstFragment)
                    return true
                }
                return false
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(false)
        return inflater.inflate(R.layout.fragment_task_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.EditTaskTitle)

        editTaskName = view.findViewById(R.id.EditTaskname)
        editTime = view.findViewById(R.id.EditTime)

        editTaskName.setText(selectedTask?.TaskName)
        editTime.setText(selectedTask?.Time)

        editTime.isFocusableInTouchMode = true
        editTaskName.isFocusableInTouchMode = true
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            editTaskName.focusable = View.FOCUSABLE
            editTime.focusable = View.FOCUSABLE
        }
        editTaskName.setOnKeyListener(keyListener)
        editTime.setOnKeyListener(keyListener)
        Toast.makeText(context, selectedTask?.TaskName, Toast.LENGTH_SHORT).show()
    }

    fun hideKeyboard(view: View?) {
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    companion object {
        @JvmStatic
        val SelectedTask = "SELECTED_TASK"
        @JvmStatic
        val EditedTask = "EDITED_TASK"
    }
}