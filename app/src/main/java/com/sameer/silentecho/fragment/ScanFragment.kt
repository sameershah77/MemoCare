package com.sameer.silentecho.fragment

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.RESULT_CANCELED
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sameer.silentecho.R
import com.sameer.silentecho.databinding.FragmentScanBinding

import java.io.IOException
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.io.InputStream
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.model.Model
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import org.tensorflow.lite.Interpreter

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class ScanFragment : Fragment() {
    private lateinit var binding: FragmentScanBinding
    private lateinit var fadeIn:Animation
    private lateinit var fadeOut:Animation
    private lateinit var translation:Animation

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentScanBinding.inflate(inflater, container, false)
        val view = binding.root
        fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
        translation = AnimationUtils.loadAnimation(requireContext(), R.anim.translation_vertical)
        binding.med1.startAnimation(translation)
        binding.med3.startAnimation(translation)
        Handler().postDelayed({
            binding.med2.startAnimation(translation)
        },4000)
        // Camera button click
        binding.camerBtn.setOnClickListener {
            ImagePicker.with(this)
                .cameraOnly()
                .crop()
                .maxResultSize(1080, 1080)
                .start()
        }

        // Gallery button click
        binding.galleryBtn.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .crop()
                .maxResultSize(1080, 1080)
                .start()
        }

        return view
    }

    // Handle the result from the ImagePicker (camera or gallery)
    @Deprecated("This method has been deprecated. Use Activity Result API instead.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImagePicker.REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val uri = data?.data
                    if (uri != null) {
                        try {
                            // Load the selected image as a Bitmap
                            val inputStream: InputStream? = uri?.let { requireContext().contentResolver.openInputStream(it) }
                            val bitmap = BitmapFactory.decodeStream(inputStream)
         
                            // Proceed to run inference on the selected image
                            bitmap?.let {
                                runModel(it)
                            }

                        } catch (e: IOException) {
                            Log.e("ImagePickerError", "Error loading image: ${e.localizedMessage}")
                        }
                    }
                }
                ImagePicker.RESULT_ERROR -> {
                    val errorMessage = ImagePicker.getError(data)
                    Log.e("ImagePickerError", errorMessage)
                }
                RESULT_CANCELED -> {
                    Log.e("ImagePickerError", "Cancelled")
                }
            }
        }
    }


    private lateinit var interpreter: Interpreter

    private fun runModel(bitmap: Bitmap) {
        // Load the model from assets
        val model = loadModelFile("breast_cancer_model.tflite")

        // Prepare the interpreter
        interpreter = Interpreter(model)

        // Preprocess the bitmap: Resize the image to model input size (e.g., 224x224)
        val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resizedBitmap)

        // Prepare input buffer
        val inputBuffer = tensorImage.buffer

        // Prepare output buffer - Assuming the model outputs a single float value (0 to 1)
        val outputBuffer = ByteBuffer.allocateDirect(1 * 4).order(ByteOrder.nativeOrder()) // Single float output, 4 bytes

        // Run the interpreter with the input and output
        interpreter.run(inputBuffer, outputBuffer)

        // Get the output result
        outputBuffer.rewind()  // Reset buffer position to zero
        val outputArray = FloatArray(1) // Since there is one output
        outputBuffer.asFloatBuffer().get(outputArray)

        // Get the prediction value
        val prediction = outputArray[0]

        // Interpret the prediction (assuming threshold of 0.5)
        val resultText = if (prediction < 0.5) {
            "Result: Benign with confidence ${(1 - prediction) * 100}%"
        } else {
            "Result: Malignant with confidence ${prediction * 100}%"
        }

        val predictionFlag = if (prediction < 0.5) { "Benign" } else { "Malignant" }
        val predictionValue = if(prediction < 0.5) { (1 - prediction) * 100 } else { prediction * 100 }


        // Log and display the result
        Log.d("Breast", "Prediction: $prediction")
        binding.predictionFlag.text = predictionFlag
        binding.predictionValue.text = "Confidence is ${predictionValue}%"

        if(binding.cancerDetails.visibility == View.GONE) {
            changeCardHeight(120,440,binding.root)

            Handler().postDelayed({
                binding.cancerDetails.visibility = View.VISIBLE
                binding.cancerDetails.startAnimation(fadeIn)
            },500)

            binding.hideIcons.startAnimation(fadeOut)
            Handler().postDelayed({
                binding.hideIcons.visibility = View.GONE
                binding.showBreast.setImageBitmap(bitmap)
                binding.showBreast.startAnimation(fadeIn)
            },800)
        }
        showPiaChart(predictionFlag, predictionValue)
    }

    // Function to load the model from assets
    private fun loadModelFile(fileName: String): ByteBuffer {
        val assetFileDescriptor = requireContext().assets.openFd(fileName)
        val fileInputStream = assetFileDescriptor.createInputStream()
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
    private fun changeCardHeight(initialHeightInDP: Int, finalHeightInDP: Int, view: View) {
        val scale = resources.displayMetrics.density
        val initialHeight = (initialHeightInDP * scale + 0.5f).toInt()
        val finalHeight = (finalHeightInDP * scale + 0.5f).toInt()
        val layoutParams = binding.cardArea.layoutParams
        layoutParams.height = finalHeight
        binding.cardArea.layoutParams = layoutParams
        val animator = ValueAnimator.ofInt(initialHeight, finalHeight)
        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            layoutParams.height = animatedValue
            binding.cardArea.layoutParams = layoutParams
        }
        animator.duration = 500
        animator.start()
    }

    fun showPiaChart(predictionFlag: String, predictionValue: Float) {
        val entries = ArrayList<PieEntry>()

        // Assign the values based on the prediction flag
        val benignValue: Float
        val malignantValue: Float

        if (predictionFlag == "Benign") {
            benignValue = predictionValue
            malignantValue = 100 - predictionValue
        } else {
            malignantValue = predictionValue
            benignValue = 100 - predictionValue
        }

        // Add the two entries for Benign and Malignant
        entries.add(PieEntry(benignValue, "Benign"))
        entries.add(PieEntry(malignantValue, "Malignant"))

        // Create the colors for the chart
        val colors = ArrayList<Int>()
        colors.add(Color.parseColor("#4CAF50")) // Green for Benign
        colors.add(Color.parseColor("#F44336")) // Red for Malignant

        // Set up the PieDataSet
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        dataSet.valueTextSize = 12f // Adjust the text size here

        // Create PieData with the dataset
        val data = PieData(dataSet)

        // Configure the PieChart
        binding.pieChart.data = data
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.isDrawHoleEnabled = false
        binding.pieChart.setHoleColor(Color.WHITE)
        binding.pieChart.setTransparentCircleRadius(61f)

        // Show only the labels on the slices (Benign/Malignant)
        binding.pieChart.setEntryLabelColor(Color.BLACK) // Label text color
        binding.pieChart.setEntryLabelTextSize(12f) // Label text size

        // Hide the legend
        val legend = binding.pieChart.legend
        legend.isEnabled = false

        // Animate the chart
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)
    }
}
