package com.example.ikuya.missionalertclock.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.take_photo_activity.*
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.example.ikuya.missionalertclock.R


typealias ODetection = (odt: Array<String?>) -> Unit
private const val TAG = "CameraXBasic"

class TakePhotoActivity : AppCompatActivity() {
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private lateinit var bottomSheetBehavier: BottomSheetBehavior<LinearLayout>

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.take_photo_activity)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        // BottomSheetを設定
        bottomSheetBehavier = BottomSheetBehavior.from(bottomSheetLayout)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        // Setup the listener for take photo button
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.settingsFragment) {
            return true
        }
        when (item.getItemId()) {
            android.R.id.home -> finish()
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
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

    //MARK:  ===== カメラ起動 =====
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        val frameLayout = FrameLayout(this)
        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            preview = Preview.Builder()
                .build()

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    // OCRの結果
                    it.setAnalyzer(cameraExecutor, ImageAnalyze { txtArr ->
                        var showTxt = ""
                        frameLayout.removeAllViews()
                        for (txt in txtArr) {
                            txt?.let {
                                showTxt += " $txt"
                            }
                        }
                        bottomSheetText.text = showTxt
                        Log.d(TAG, "listener fired!: $showTxt")
                    })
                }

            // Select back camera
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )
                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider(camera?.cameraInfo))
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    // 画像分析 - フレームごとにオブジェクト分類
    class ImageAnalyze(private val listener: ODetection): ImageAnalysis.Analyzer {
        val options = FirebaseVisionOnDeviceImageLabelerOptions.Builder()
            .setConfidenceThreshold(0.7f)
            .build()
        val labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler(options)

        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer

        private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
            0 -> FirebaseVisionImageMetadata.ROTATION_0
            90 -> FirebaseVisionImageMetadata.ROTATION_90
            180 -> FirebaseVisionImageMetadata.ROTATION_180
            270 -> FirebaseVisionImageMetadata.ROTATION_270
            else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
        }

        // フレームごとに呼ばれる
        override fun analyze(image: ImageProxy) {
            // Pass image to an ML Kit Vision API
            doObjectClassification(image)
        }

        // 画像分類
        @SuppressLint("UnsafeExperimentalUsageError")
        private fun doObjectClassification(proxy: ImageProxy) {
            val mediaImage = proxy.image ?: return
            val imageRotation = degreesToFirebaseRotation(proxy.imageInfo.rotationDegrees)
            val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
            labeler.processImage(image)
                .addOnSuccessListener { labels ->
                    // Task completed successfully
                    for (label in labels) {
                        val text = label.text
                        Log.d(TAG, "text: $text")
                        if (text == "Paper") {
                            doTextRecognition(image)
                        } else {
                            // do something
                        }
                    }
                    proxy.close()
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    Log.e(TAG, e.toString())
                    proxy.close()
                }
        }

        //文字認識 - 書類に書かれた文字のみ認識する　
        private fun doTextRecognition(image: FirebaseVisionImage) {
            val result = detector.processImage(image)
                .addOnSuccessListener { firebaseVisionText ->
                    // Task completed successfully
                    parseResultText(firebaseVisionText)
                    Log.d(TAG, "OCR Succeeded!")
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    Log.d(TAG, "OCR Failed...")
                    Log.e(TAG, e.toString())
                }
        }

        // パース - OCRで認識された文字列をParseする
        private fun parseResultText(result: FirebaseVisionText) {
            var resultTxtList:Array<String?> = arrayOf(null)
            for (block in result.textBlocks) {
                val blockText = block.text
                // If you need more data, Uncommentout here
//                val blockConfidence = block.confidence
//                val blockLanguages = block.recognizedLanguages
//                val blockCornerPoints = block.cornerPoints
//                val blockFrame = block.boundingBox
                resultTxtList += blockText
                // If you need more data, Uncommentout here
//                for (line in block.lines) {
//                    val lineText = line.text
//                    val lineConfidence = line.confidence
//                    val lineLanguages = line.recognizedLanguages
//                    val lineCornerPoints = line.cornerPoints
//                    val lineFrame = line.boundingBox
//                    for (element in line.elements) {
//                        val elementText = element.text
//                        val elementConfidence = element.confidence
//                        val elementLanguages = element.recognizedLanguages
//                        val elementCornerPoints = element.cornerPoints
//                        val elementFrame = element.boundingBox
//                    }
//                }
            }
            Log.d("RESULT_TEXT", resultTxtList.toString())
            listener(resultTxtList)
        }
    }
}

//import android.app.Activity
//import android.content.Context
//import android.content.Context.CAMERA_SERVICE
//import android.graphics.Bitmap
//import android.hardware.camera2.CameraAccessException
//import android.hardware.camera2.CameraCharacteristics
//import android.hardware.camera2.CameraManager
//import android.media.Image
//import android.net.Uri
//import android.os.Build
//import android.util.SparseIntArray
//import android.view.Surface
//import androidx.annotation.RequiresApi
//import com.google.mlkit.vision.common.InputImage
//import java.io.IOException
//import java.nio.ByteBuffer
//
//
//class MLKitVisionImage {
//
//    private fun imageFromBitmap(bitmap: Bitmap) {
//        val rotationDegrees = 0
//        // [START image_from_bitmap]
//        val image = InputImage.fromBitmap(bitmap, 0)
//        // [END image_from_bitmap]
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private fun imageFromMediaImage(mediaImage: Image, rotation: Int) {
//        // [START image_from_media_image]
//        val image = InputImage.fromMediaImage(mediaImage, rotation)
//        // [END image_from_media_image]
//    }
//
//    private fun imageFromBuffer(byteBuffer: ByteBuffer, rotationDegrees: Int) {
//        // [START set_metadata]
//        //
//        // [END set_metadata]
//        // [START image_from_buffer]
//        val image = InputImage.fromByteBuffer(
//            byteBuffer,
//            /* image width */ 480,
//            /* image height */ 360,
//            rotationDegrees,
//            InputImage.IMAGE_FORMAT_NV21 // or IMAGE_FORMAT_YV12
//        )
//        // [END image_from_buffer]
//    }
//
//    private fun imageFromArray(byteArray: ByteArray, rotationDegrees: Int) {
//        // [START image_from_array]
//        val image = InputImage.fromByteArray(
//            byteArray,
//            /* image width */ 480,
//            /* image height */ 360,
//            rotationDegrees,
//            InputImage.IMAGE_FORMAT_NV21 // or IMAGE_FORMAT_YV12
//        )
//
//        // [END image_from_array]
//    }
//
//    private fun imageFromPath(context: Context, uri: Uri) {
//        // [START image_from_path]
//        val image: InputImage
//        try {
//            image = InputImage.fromFilePath(context, uri)
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//        // [END image_from_path]
//    }
//
//    // [START get_rotation]
//    /**
//     * Get the angle by which an image must be rotated given the device's current
//     * orientation.
//     */
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Throws(CameraAccessException::class)
//    private fun getRotationCompensation(cameraId: String, activity: Activity, isFrontFacing: Boolean): Int {
//        // Get the device's current rotation relative to its "native" orientation.
//        // Then, from the ORIENTATIONS table, look up the angle the image must be
//        // rotated to compensate for the device's rotation.
//        val deviceRotation = activity.windowManager.defaultDisplay.rotation
//        var rotationCompensation = ORIENTATIONS.get(deviceRotation)
//
//        // Get the device's sensor orientation.
//        val cameraManager = activity.getSystemService(CAMERA_SERVICE) as CameraManager
//        val sensorOrientation = cameraManager
//            .getCameraCharacteristics(cameraId)
//            .get(CameraCharacteristics.SENSOR_ORIENTATION)!!
//
//        if (isFrontFacing) {
//            rotationCompensation = (sensorOrientation + rotationCompensation) % 360
//        } else { // back-facing
//            rotationCompensation = (sensorOrientation - rotationCompensation + 360) % 360
//        }
//        return rotationCompensation
//    }
//    // [END get_rotation]
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Throws(CameraAccessException::class)
//    private fun getCompensation(activity: Activity, context: Context, isFrontFacing: Boolean) {
//        // Get the ID of the camera using CameraManager. Then:
//        val rotation = getRotationCompensation(MY_CAMERA_ID, activity, isFrontFacing)
//    }
//
//    companion object {
//
//        private val TAG = "MLKIT"
//        private val MY_CAMERA_ID = "my_camera_id"
//
//        // [START camera_orientations]
//        private val ORIENTATIONS = SparseIntArray()
//
//        init {
//            ORIENTATIONS.append(Surface.ROTATION_0, 0)
//            ORIENTATIONS.append(Surface.ROTATION_90, 90)
//            ORIENTATIONS.append(Surface.ROTATION_180, 180)
//            ORIENTATIONS.append(Surface.ROTATION_270, 270)
//        }
//        // [END camera_orientations]
//    }
//}