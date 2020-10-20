package com.example.scheduler.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.scheduler.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraTest : Fragment() {

    private var imageCapture: ImageCapture? = null

    private lateinit var previewSurface: PreviewView
    private lateinit var camExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        previewSurface = view.findViewById(R.id.CameraPreview)

        if(allPermissionsGranted())
            startCamera()
        else
            ActivityCompat.requestPermissions(requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE)


        camExecutor = Executors.newSingleThreadExecutor()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG,"onDestroy")
        camExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE){
            if(allPermissionsGranted())
                startCamera()
            else {
                Toast.makeText(requireContext(), "Please give permissions", Toast.LENGTH_SHORT)
                    .show()
                requireActivity().finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera(){

        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener(Runnable {

            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewSurface.surfaceProvider)
                }

            val camSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(requireContext() as LifecycleOwner, camSelector, preview)
            }catch (exe: Exception){
                Log.e(TAG, "Error in startCamera: $exe")
            }

        }, ContextCompat.getMainExecutor(requireContext()))

    }

    companion object{
        private const val TAG = "tag"
        private const val REQUEST_CODE = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

        val instance: CameraTest = CameraTest()

    }

}