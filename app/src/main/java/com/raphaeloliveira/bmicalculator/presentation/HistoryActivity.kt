package com.raphaeloliveira.bmicalculator.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.raphaeloliveira.bmicalculator.BmiCalculatorApplication
import com.raphaeloliveira.bmicalculator.R
import com.raphaeloliveira.bmicalculator.data.BmiCalculatorDataBase
import com.raphaeloliveira.bmicalculator.data.HistoryDao
import com.raphaeloliveira.bmicalculator.databinding.ActivityHistoryBinding
import com.raphaeloliveira.bmicalculator.domain.ui.HistoryUiData
import com.raphaeloliveira.bmicalculator.domain.usecase.DeleteHistoryUseCase
import com.raphaeloliveira.bmicalculator.domain.usecase.GetHistoryUseCase
import com.raphaeloliveira.bmicalculator.presentation.viewmodel.HistoryViewModel
import com.raphaeloliveira.bmicalculator.presentation.viewmodel.HistoryViewModelFactory
import org.koin.android.ext.android.inject


class HistoryActivity : AppCompatActivity(){

    private lateinit var binding: ActivityHistoryBinding

    private var historyList = listOf<HistoryUiData>()

    private val historyAdapter = HistoryAdapter()

    lateinit var db: BmiCalculatorDataBase

    private val historyDao: HistoryDao by inject()

    private val viewModel: HistoryViewModel by viewModels{
        val getHistoryUseCase = GetHistoryUseCase(historyDao)
        val deleteHistoryUseCase = DeleteHistoryUseCase(historyDao)
        HistoryViewModelFactory(getHistoryUseCase, deleteHistoryUseCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvHistory.adapter = historyAdapter
        val historyDeleted = getString(R.string.history_deleted)

        historyAdapter.setOnDeleteListener { history ->
            viewModel.deleteHistory(history)
            Toast.makeText(this, (historyDeleted), Toast.LENGTH_SHORT).show()

            viewModel.getHistoryFromDatabase { history ->
                historyList = history
                historyAdapter.submitList(historyList)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        db = (application as BmiCalculatorApplication).db

        viewModel.getHistoryFromDatabase { history ->
            historyList = history
            historyAdapter.submitList(historyList)
        }
    }
}