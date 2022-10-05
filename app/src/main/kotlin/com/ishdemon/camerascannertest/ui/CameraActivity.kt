package com.ishdemon.camerascannertest.ui

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.ishdemon.camerascannertest.data.domain.Album
import com.ishdemon.camerascannertest.data.domain.Image
import com.ishdemon.camerascannertest.databinding.ActivityCameraBinding
import com.ishdemon.camerascannertest.ui.preview.PreviewActivity
import com.ishdemon.camerascannertest.ui.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

@AndroidEntryPoint
class CameraActivity: AppCompatActivity() {

    private lateinit var viewBinding: ActivityCameraBinding
    private val viewModel by viewModels<PhotosViewModel>()
    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService
    private var count: AtomicInteger = AtomicInteger(0)
    private lateinit var albumId: String
    private var currentAlbum: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        albumId = "Album-${SimpleDateFormat(ALBUM_FORMAT, Locale.US).format(System.currentTimeMillis())}"
        // Set up the listeners for take photo and video capture buttons
        viewBinding.button.setOnClickListener { takePhoto() }
        viewBinding.imageView.setOnClickListener {
            PreviewActivity.launch(this,albumId)
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "$IMAGES_PATH/$albumId")
            } else {
                createFolderIfNotExist()
                put(MediaStore.Images.Media.DATA, "${Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES
                )}/CameraScanner/$albumId/$name .jpg")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Glide.with(this@CameraActivity).load(output.savedUri).into(viewBinding.imageView)
                    viewBinding.textViewCount.apply {
                        text = count.incrementAndGet().toString()
                        isVisible = true
                    }
                    Log.d(TAG, msg)
                    saveToDb(output,name)
                }
            }
        )
    }


    private fun saveToDb(output: ImageCapture.OutputFileResults, fileName: String) {
        if(count.get() == 1){
            currentAlbum = Album(
                id = albumId,
                album_name = albumId,
                thumbUri = output.savedUri.toString(),
                count = count.get(),
                album_date = getDateString()
            ).also {
                viewModel.addAlbum(it)
            }
        } else currentAlbum?.let {
            viewModel.updateAlbum(it.copy(count = count.get()))
        }

        viewModel.addImage(
            Image(
                fileName = fileName,
                album = albumId,
                uri = output.savedUri.toString(),
                timeStamp = Date().time
            )
        )
    }

    private fun getFiles() {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/CameraScanner"
        Log.d("Files", "Path: $path")
        val directory = File(path)
        val files = directory.listFiles()
        if (files != null) {
            Log.d("Files", "Size: " + files.size)
        }
        if (files != null) {
            for (i in files.indices) {
                Log.d("Files", "FileName:" + files[i].name)
            }
        }
    }

    private fun getDateString(): String {
        return SimpleDateFormat("dd MMM yyyy").format(Date())
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()


            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun createFolderIfNotExist() {
        val file = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ).toString() + "/CameraScanner/$albumId"
        )
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.d(TAG, "Folder Create -> Failure")
            } else {
                Log.d(TAG, "Folder Create -> Success")
            }
        }
    }

    companion object {
        private val IMAGES_PATH = "${Environment.DIRECTORY_PICTURES}/CameraScanner"
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val ALBUM_FORMAT = "HHmmssSSS-yyyy-MM-dd"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
        private const val PARAMS = "params"
        fun launch(activity: Activity, requestCode: Int = -1) = activity.startActivityForResult(
            Intent(activity, CameraActivity::class.java), requestCode)

    }
}
