package com.raphaeloliveira.bmicalculator

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.raphaeloliveira.bmicalculator.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityResultBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val result = intent.getFloatExtra(Constants.KEY_RESULT, 0f)

    binding.tvResult.text = result.toString()
    binding.bmiIndicatorView.imcValue(result)

    val classificationText = when {
        result < 18.5f -> getString(R.string.underweight)
        result in 18.5f..24.9f -> getString(R.string.healthyweight)
        result in 25f..29.9f -> getString(R.string.overweight)
        result in 30f..39.9f -> getString(R.string.obesity)
        else -> getString(R.string.severe_obesity)
    }

    val classificationColor = when {
        result < 18.5f -> R.color.colorUnderWeight
        result in 18.5f..24.9f -> R.color.colorNormal
        result in 25f..29.9f -> R.color.colorOverWeight
        result in 30f..39.9f -> R.color.colorObesity
        else -> R.color.colorSevereObesity
    }

    binding.tvClassification.text = classificationText
    binding.tvClassification.setTextColor(ContextCompat.getColor(this, classificationColor))
}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       finish()
        return super.onOptionsItemSelected(item)
    }
}