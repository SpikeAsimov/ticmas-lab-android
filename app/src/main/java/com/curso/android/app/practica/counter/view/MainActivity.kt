package com.curso.android.app.practica.counter.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.curso.android.app.practica.counter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.counter.observe(this) {
            println("Recibimos un nuevo valor de counter. $it")
            binding.counter.text = "${it.number}"
            binding.timestamp.text = "Actualizado ${it.timestamp}"
        }

        binding.incrementButton.setOnClickListener {
            mainViewModel.incrementCounter()
        }
        binding.decrementButton.setOnClickListener {
            mainViewModel.decrementCounter()
        }

        mainViewModel.resultaLiveData.observe(this) { result ->
            binding.resultTextView.text = result
        }

        binding.compareButton.setOnClickListener(){
            val text1 = binding.editText1.text.toString()
            val text2 = binding.editText2.text.toString()
            mainViewModel.compareTexts(text1, text2)
        }
    }
}
