package com.example.ikuya.missionalertclock.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.system.Os.close
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.SurfaceView
import android.view.TextureView
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.google.firebase.ml.vision.FirebaseVision
//import com.google.firebase.ml.vision.common.FirebaseVisionImage
//import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
//import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions
//import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.take_photo_activity.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.example.ikuya.missionalertclock.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.vision.text.TextBlock
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import java.nio.ByteBuffer


class TakePhotoActivity : AppCompatActivity() {

    private lateinit var bottomSheetBehavier: BottomSheetBehavior<LinearLayout>
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.take_photo_activity)
        bottomSheetBehavier = BottomSheetBehavior.from(bottomSheetLayout)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Set up the listener for take photo button

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this).also {
            it.addListener(Runnable {
                val cameraProvider = cameraProviderFuture.get()
                initializeUseCases(cameraProvider)
            }, ContextCompat.getMainExecutor(this))
        }
    }

    private fun initializeUseCases(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .build()

        val cameraSelector = CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()

        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, ImageAnalyze(
                    bottomSheetText.text = result
                ))
            }



        val camera = cameraProvider.bindToLifecycle(
            this as LifecycleOwner,
            cameraSelector,
            preview,
            imageAnalyzer
        )

        preview.setSurfaceProvider(view_finder.createSurfaceProvider(camera.cameraInfo))

    }




    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    //CameraXによる画像解析
    private class ImageAnalyze : ImageAnalysis.Analyzer {

        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                doObjectClassification(image)

            }
        }

        fun doObjectClassification(image: InputImage) {
            val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)

            labeler.process(image)
                .addOnSuccessListener { labels ->
                    for (label in labels) {
                        val text = label.text
                        Log.d(TAG, "text: $text")
                        if (text == "Paper") {
                            doTextRecognition(image)
                        } else {
                            // do something
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, e.toString())
                }

        }


        private fun doTextRecognition(image: InputImage) {
            val recognizer = TextRecognition.getClient()
            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    // Task completed successfully
                    parseResultText(visionText)
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    // ...
                }


        }

        private fun parseResultText(result: Text) {
            val resultText = result.text
            for (block in result.textBlocks) {
                val blockText = block.text
                val blockCornerPoints = block.cornerPoints
                val blockFrame = block.boundingBox
                for (line in block.lines) {
                    val lineText = line.text
                    val lineCornerPoints = line.cornerPoints
                    val lineFrame = line.boundingBox
                    for (element in line.elements) {
                        val elementText = element.text
                        val elementCornerPoints = element.cornerPoints
                        val elementFrame = element.boundingBox
                    }
                }
            }
            resultText
        }
    }
}














