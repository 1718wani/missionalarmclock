package com.example.ikuya.missionalertclock.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import java.nio.ByteBuffer

typealias LumaListener = (luma: Double) -> Unit


class TakePhotoActivity : AppCompatActivity(){

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
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
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
            baseContext, it) == PackageManager.PERMISSION_GRANTED
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
            .build()
            .also {
                it.setAnalyzer(cameraExecutor,ImageAnalyze { luma ->
                    Log.d(TAG, "🎈Average luminosity: $luma")
                })
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


    private class ImageAnalyze (private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()



            listener(luma)

            image.close()
        }


//    private class ImageAnalyze (private val listener: LumaListener) : ImageAnalysis.Analyzer {
//
//        private fun ByteBuffer.toByteArray(): ByteArray {
//            rewind()    // Rewind the buffer to zero
//            val data = ByteArray(remaining())
//            get(data)   // Copy the buffer into a byte array
//            return data // Return the byte array
//        }
//
//        override fun analyze(image: ImageProxy) {
//
//            val buffer = image.planes[0].buffer
//            val data = buffer.toByteArray()
//            val pixels = data.map { it.toInt() and 0xFF }
//            val luma = pixels.average()
//
//
//
//            listener(luma)
//
//            image.close()
//        }





    }






}






//typealias ODetection = (odt: Array<String?>) -> Unit
//private const val TAG = "CameraXBasic"
//
//class TakePhotoActivity : AppCompatActivity() {
//    private var preview: Preview? = null
//    private var imageCapture: ImageCapture? = null
//    private var imageAnalyzer: ImageAnalysis? = null
//    private var camera: Camera? = null
//    private lateinit var bottomSheetBehavier: BottomSheetBehavior<LinearLayout>
//
//    private lateinit var outputDirectory: File
////    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
//
//    private lateinit var cameraExecutor: ExecutorService
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.take_photo_activity)
//
////        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
////        supportActionBar!!.setHomeButtonEnabled(true)
//
//        // BottomSheetを設定
//        bottomSheetBehavier = BottomSheetBehavior.from(bottomSheetLayout)
//
//        // Request camera permissions
//        if (allPermissionsGranted()) {
//            startCamera()
//        } else {
//            ActivityCompat.requestPermissions(
//                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
//            )
//        }
//        // Setup the listener for take photo button
//        outputDirectory = getOutputDirectory()
//        cameraExecutor = Executors.newSingleThreadExecutor()
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id: Int = item.itemId
//        if (id == R.id.settingsFragment) {
//            return true
//        }
//        when (item.itemId) {
//            android.R.id.home -> finish()
//            else -> {
//                Log.e(TAG,"Is it Ok" )
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int, permissions: Array<String>, grantResults:
//        IntArray
//    ) {
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (allPermissionsGranted()) {
//                startCamera()
//                Log.e(TAG,"kameragakidousitayon")
//            } else {
//                Toast.makeText(
//                    this,
//                    "Permissions not granted by the user.",
//                    Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }
//
//
//
//    //MARK:  ===== カメラ起動 =====
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        val frameLayout = FrameLayout(this)
//        cameraProviderFuture.addListener(Runnable {
//            // Used to bind the lifecycle of cameras to the lifecycle owner
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//
//            // Preview
//            preview = Preview.Builder()
//                .build()
//
//            imageCapture = ImageCapture.Builder()
//                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
//                .build()
//
//            imageAnalyzer = ImageAnalysis.Builder()
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build()
//                .also {
//                    // OCRの結果
//                    it.setAnalyzer(cameraExecutor, ImageAnalyze { txtArr ->
//                        var showTxt = ""
//                        frameLayout.removeAllViews()
//                        for (txt in txtArr) {
//                            txt?.let {
//                                showTxt += " $txt"
//                            }
//                        }
//                        bottomSheetText.text = showTxt
//                        Log.d(TAG, "listener fired!: $showTxt")
//                    })
//                }
//
////             Select back camera
//            val cameraSelector =
//                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
//
//            try {
//                // Unbind use cases before rebinding
//                cameraProvider.unbindAll()
//
//                // Bind use cases to camera
//                camera = cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview, imageCapture, imageAnalyzer
//                )
//                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider(camera?.cameraInfo))
//            } catch (exc: Exception) {
//                Log.e(TAG, "Use case binding failed", exc)
//            }
//
//        }, ContextCompat.getMainExecutor(this))
//    }
//
//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(
//            baseContext, it
//        ) == PackageManager.PERMISSION_GRANTED
//    }
//
//    private fun getOutputDirectory(): File {
//        val mediaDir = externalMediaDirs.firstOrNull()?.let {
//            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
//        return if (mediaDir != null && mediaDir.exists())
//            mediaDir else filesDir
//    }
//
//    companion object {
//        private const val REQUEST_CODE_PERMISSIONS = 10
//        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
//    }
//
//
//
//    // 画像分析 - フレームごとにオブジェクト分類
//    class ImageAnalyze(private val listener: ODetection): ImageAnalysis.Analyzer {
//        val options = FirebaseVisionOnDeviceImageLabelerOptions.Builder()
//            .setConfidenceThreshold(0.7f)
//            .build()
//        val labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler(options)
//
//        val detector = FirebaseVision.getInstance()
//            .onDeviceTextRecognizer
//
//        private fun degreesToFirebaseRotation(degrees: Int): Int = when (degrees) {
//            0 -> FirebaseVisionImageMetadata.ROTATION_0
//            90 -> FirebaseVisionImageMetadata.ROTATION_90
//            180 -> FirebaseVisionImageMetadata.ROTATION_180
//            270 -> FirebaseVisionImageMetadata.ROTATION_270
//            else -> throw Exception("Rotation must be 0, 90, 180, or 270.")
//        }
//
//        // フレームごとに呼ばれる
//        override fun analyze(image: ImageProxy) {
//            // Pass image to an ML Kit Vision API
//            doObjectClassification(image)
//            Log.e(TAG,"analyze")
//            image.close()
//            return
//
//        }
//
//        // 画像分類
//        @SuppressLint("UnsafeExperimentalUsageError")
//        private fun doObjectClassification(proxy: ImageProxy) {
//            val mediaImage = proxy.image ?: return
//            val imageRotation = degreesToFirebaseRotation(proxy.imageInfo.rotationDegrees)
//            val image = FirebaseVisionImage.fromMediaImage(mediaImage, imageRotation)
//            labeler.processImage(image)
//                .addOnSuccessListener { labels ->
//                    // Task completed successfully
//                    for (label in labels) {
//                        val text = label.text
//                        Log.d(TAG, "text: $text")
//                        if (text == "Paper") {
//                            doTextRecognition(image)
//                        } else {
//                            // do something
//                            Log.e(TAG,"Is it Ok" )
//                        }
//                    }
//                    proxy.close()
//                }
//                .addOnFailureListener { e ->
//                    // Task failed with an exception
//                    Log.e(TAG, e.toString())
//                    proxy.close()
//                }
//        }
//
//        //文字認識 - 書類に書かれた文字のみ認識する　
//        private fun doTextRecognition(image: FirebaseVisionImage) {
//            val result = detector.processImage(image)
//                .addOnSuccessListener { firebaseVisionText ->
//                    // Task completed successfully
//                    parseResultText(firebaseVisionText)
//                    Log.d(TAG, "OCR Succeeded!")
//                }
//                .addOnFailureListener { e ->
//                    // Task failed with an exception
//                    Log.d(TAG, "OCR Failed...")
//                    Log.e(TAG, e.toString())
//                }
//        }
//
//        // パース - OCRで認識された文字列をParseする
//         private fun parseResultText(result: FirebaseVisionText) {
//            var resultTxtList:Array<String?> = arrayOf(null)
//            for (block in result.textBlocks) {
//                val blockText = block.text
//                // If you need more data, Uncommentout here
//                val blockConfidence = block.confidence
//                val blockLanguages = block.recognizedLanguages
//                val blockCornerPoints = block.cornerPoints
//                val blockFrame = block.boundingBox
//                resultTxtList += blockText
//                // If you need more data, Uncommentout here
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
//            }
//            Log.d("RESULT_TEXT", resultTxtList.toString())
//            listener(resultTxtList)
//        }
//    }
//}
