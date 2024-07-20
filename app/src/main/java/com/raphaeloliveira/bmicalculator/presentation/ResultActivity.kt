package com.raphaeloliveira.bmicalculator.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.raphaeloliveira.bmicalculator.data.HistoryDao
import com.raphaeloliveira.bmicalculator.databinding.ActivityResultBinding
import com.raphaeloliveira.bmicalculator.presentation.viewmodel.ResultViewModel
import com.raphaeloliveira.bmicalculator.presentation.viewmodel.ResultViewModelFactory
import com.raphaeloliveira.bmicalculator.domain.usecase.CreateHistoryUseCase
import com.raphaeloliveira.bmicalculator.util.Constants
import org.koin.android.ext.android.inject
import java.util.Locale

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    private val viewModel: ResultViewModel by viewModels{
        val createHistoryUseCase = CreateHistoryUseCase(historyDao)
        ResultViewModelFactory(createHistoryUseCase, resources)
    }

    private val historyDao: HistoryDao by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getFloatExtra(Constants.KEY_RESULT, 0.0f)
        val bmiStr = String.format(Locale.US,"%.2f", result).toFloat()

        binding.tvResult.text = bmiStr.toString()
        binding.bmiIndicatorView.bmiValue(bmiStr)

        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

        viewModel.classifyBmi(bmiStr)
        viewModel.classification.observe(this) { classification: String ->
            binding.tvClassification.text = classification

            val weight = intent.getStringExtra(Constants.KEY_WEIGHT).toString()
            val height = intent.getStringExtra(Constants.KEY_HEIGHT).toString()
            viewModel.insertBmiDataInDatabase(bmiStr, classification, weight, height)
        }

        viewModel.classificationColor.observe(this) { color: Int ->
            binding.tvClassification.setTextColor(ContextCompat.getColor(this, color))
        }
    }
}