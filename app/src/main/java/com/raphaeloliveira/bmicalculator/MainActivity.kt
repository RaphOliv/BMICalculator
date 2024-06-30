package com.raphaeloliveira.bmicalculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.raphaeloliveira.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            binding.btnCalcular.setOnClickListener {

            val alturaStr = binding.edittextAltura.text.toString()
            val pesoStr = binding.edittextPeso.text.toString()

            if (alturaStr.isNotEmpty() && pesoStr.isNotEmpty()){

                val altura: Float = alturaStr.toFloat()
                val peso: Float = pesoStr.toFloat()

                val alturaFinal: Float = altura * altura
                val imc = peso / alturaFinal


                val intent = Intent(this, ResultActivity::class.java)
                    .apply {
                        putExtra("EXTRA_RESULT", imc)
                    }

                startActivity(intent)

            } else {
                Snackbar.make(binding.edittextPeso,
                    "Preencher todos os campos", Snackbar.LENGTH_LONG).show()

            }
        }
    }
}