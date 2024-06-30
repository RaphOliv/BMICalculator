package com.raphaeloliveira.bmicalculator

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.raphaeloliveira.bmicalculator.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result = intent.getFloatExtra("EXTRA_RESULT", 0.1f)
        val imcformatado = String.format("%.2f" , result)

        binding.textviewResult.text = imcformatado

        val classificacao = if(result < 18.5f) {
            "ABAIXO DO PESO"
        }else if(result in 18.5f..24.9f){
            "NORMAL"
        }else if(result in 25f..29.9f){
            "SOBREPESO"
        }else if(result in 30f..39.9f){
            "OBESIDADE"
        }else{
            "OBESIDADE GRAVE"
        }

        binding.textviewClassificacao.text = getString(R.string.message_classificacao, classificacao)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       finish()
        return super.onOptionsItemSelected(item)
    }
}