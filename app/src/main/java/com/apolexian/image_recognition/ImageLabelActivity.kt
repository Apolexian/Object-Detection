package com.apolexian.image_recognition

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.android.synthetic.main.activity_main.*

class ImageLabelActivity : BaseCameraActivity() {

    private val itemAdapter: ImageLabelAdapter by lazy {
        ImageLabelAdapter(listOf())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this);
        rvLabel.layoutManager = LinearLayoutManager(this)
        rvLabel.adapter = itemAdapter
    }

    private fun runCloudImageLabeling(bitmap: Bitmap) {
        //Create a FirebaseVisionImage
        val image = FirebaseVisionImage.fromBitmap(bitmap)

        //Get access to an instance of FirebaseCloudImageDetector
        val detector = FirebaseVision.getInstance().cloudImageLabeler

        //Use the detector to detect the labels inside the image
        detector.processImage(image)
            .addOnSuccessListener {
                // Task completed successfully
                progressBar.visibility = View.GONE
                itemAdapter.setList(it)
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            }
            .addOnFailureListener {
                // Task failed with an exception
                progressBar.visibility = View.GONE
                Toast.makeText(baseContext, "Sorry, something went wrong!", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    override fun onClick(v: View?) {
        progressBar.visibility = View.VISIBLE
        cameraView.captureImage { cameraKitImage ->
            // Get the Bitmap from the captured shot
            runCloudImageLabeling(cameraKitImage.bitmap)
            runOnUiThread {
                showPreview()
                imagePreview.setImageBitmap(cameraKitImage.bitmap)
            }
        }
    }
}


