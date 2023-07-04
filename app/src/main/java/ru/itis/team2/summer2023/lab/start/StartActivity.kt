package ru.itis.team2.summer2023.lab.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.itis.team2.summer2023.lab.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
