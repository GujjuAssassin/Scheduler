package com.example.scheduler.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scheduler.Database.Task
import com.example.scheduler.R
import com.example.scheduler.RVAdapter
import com.example.scheduler.fragments.SecondFragment.Companion.EditFragTag
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*


class FirstFragment : Fragment() {

    private lateinit var listViewer: RecyclerView
    private lateinit var listAdapter: RVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        listAdapter = RVAdapter(requireContext(), findNavController())
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listViewer = view.findViewById(R.id.listViewer)
        listViewer.adapter = listAdapter
        listViewer.layoutManager = LinearLayoutManager(view.context)

        activity?.title = getString(R.string.ScheduleList)

        if (arguments != null) {
            if (requireArguments().containsKey(EditFragTag)) {
                listAdapter.addItem(arguments?.get(EditFragTag).toString())
                Toast.makeText(
                    context,
                    "Adding New Task: ${arguments?.get(EditFragTag).toString()}",
                    Toast.LENGTH_SHORT
                ).show()
                arguments = null
            } else if (requireArguments().containsKey(TaskEditFragment.EditedTask)) {
                val updatedTask = arguments?.getParcelable<Task>(TaskEditFragment.EditedTask)
                updatedTask?.let { listAdapter.updateTask(it) }
                Toast.makeText(context, "Updating Task", Toast.LENGTH_SHORT).show()
            }
            arguments = null
        }

        view.findViewById<FloatingActionButton>(R.id.addNewTaskButton).setOnClickListener {
            val bun: Bundle = bundleOf(EditFragTag to "information")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bun)
        }
    }


}