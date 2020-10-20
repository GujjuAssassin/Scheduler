package com.example.scheduler.fragments

import android.app.Activity
import android.graphics.Camera
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.example.scheduler.R
import kotlinx.android.synthetic.main.fragment_second.*


class SecondFragment : Fragment() {

    private var isCamBg = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(false)
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.title = getString(R.string.AddTaskTitle)
        val et = view.findViewById<EditText>(R.id.taskNameEditText)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            findNavController().navigate(
                R.id.action_SecondFragment_to_FirstFragment, bundleOf(
                    EditFragTag to et.text.toString()
                )
            )
        }

        childFragmentManager.commit {
            add(R.id.background, CameraTest.instance, CAMERA_FRAGMENT)
        }

        removeBg.setOnClickListener{

            if(isCamBg){
                childFragmentManager.beginTransaction().remove(CameraTest.instance).commit()
                (it as Button).text = "Enable CamBackground"
                isCamBg = false
            }else{
                childFragmentManager.beginTransaction().add(R.id.background, CameraTest.instance, CAMERA_FRAGMENT).commit()
                (it as Button).text = "Remove CamBackground"
                isCamBg = true
            }
        }

    }

    companion object {
        @JvmStatic
        val EditFragTag: String = "TASK_NAME"
        @JvmStatic
        val CAMERA_FRAGMENT = "CameraFragment"
    }

}