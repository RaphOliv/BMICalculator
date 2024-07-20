package com.raphaeloliveira.bmicalculator.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.raphaeloliveira.bmicalculator.R
import com.raphaeloliveira.bmicalculator.databinding.ActivityMainBinding
import com.raphaeloliveira.bmicalculator.domain.BmiCalculator
import com.raphaeloliveira.bmicalculator.util.Constants
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.btnHistory.setOnClickListener {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
            }

            binding.btnCalculate.setOnClickListener {

            val heightStr = binding.etHeight.text.toString()
            val weightStr = binding.etWeight.text.toString()

            if (heightStr.isNotEmpty() && weightStr.isNotEmpty()){

                val height: Float = heightStr.toFloat()
                val weight: Float = weightStr.toFloat()

                val bmiCalculator = BmiCalculator()
                val bmi = bmiCalculator.calculateBmi(height, weight)

                val intent = Intent(this, ResultActivity::class.java)
                intent.putExtra(Constants.KEY_RESULT, bmi)
                intent.putExtra(Constants.KEY_WEIGHT, weightStr)
                intent.putExtra(Constants.KEY_HEIGHT, heightStr)
                startActivity(intent)

            } else {
                Snackbar.make(binding.etWeight,
                    getString(R.string.fill_fields), Snackbar.LENGTH_SHORT).show()

            }
        }

        binding.etHeight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.etHeight.removeTextChangedListener(this)

                try {
                    val givenString = s.toString().replace(",", "").replace(".", "")
                    if (givenString.length > 2) {
                        val longVal: Long = givenString.toLong()
                        val format = NumberFormat.getInstance(Locale.US) as DecimalFormat
                        format.applyPattern("0.00")
                        val formattedHeight = format.format(longVal / 100.00)
                        binding.etHeight.setText(formattedHeight)
                        binding.etHeight.setSelection(formattedHeight.length)
                    }
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }

                binding.etHeight.addTextChangedListener(this)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }
}
